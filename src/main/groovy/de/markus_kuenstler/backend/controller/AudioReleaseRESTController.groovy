package de.markus_kuenstler.backend.controller

import de.markus_kuenstler.backend.datasource.AudioReleaseDataSource
import de.markus_kuenstler.backend.domain.AudioRelease
import de.markus_kuenstler.backend.domain.MatchingReleases
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import java.text.SimpleDateFormat

@RestController
@RequestMapping("/api/audioreleases")
class AudioReleaseRESTController {

    Logger logger = LoggerFactory.getLogger(AudioReleaseRESTController.class)

    // normally i'd create repository class to combine all datasources and encapsulate the search logic
    // i omitted this for this task to keep it shorter
    @Autowired
    AudioReleaseDataSource audioReleaseDataSource

    @GetMapping
    public MatchingReleases getAudioReleases() {


        return convertToSearchResult(audioReleaseDataSource.audioReleases)
    }

    @GetMapping("/{name}")
    public MatchingReleases findByName(@PathVariable(name = "name") String name) {

        List<AudioRelease> searchResults = audioReleaseDataSource.getAudioReleasesByName(name)

        logger.debug("found ${searchResults.size()} AudioReleases with name ${name}")

        return convertToSearchResult(searchResults)
    }

    @GetMapping(value = "/findByProperties", params = ["releasedBefore", "minTrackCount"])
    public MatchingReleases findByProperties(
            @RequestParam("releasedBefore") String releasedBefore, @RequestParam("minTrackCount") int minTrackCount) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy")

        Date releasedBeforeDate = sdf.parse(releasedBefore)

        List<AudioRelease> searchResults = audioReleaseDataSource.audioReleases.findAll { ar -> (ar.tracks.size() >= minTrackCount) && (ar.releaseDate.before(releasedBeforeDate)) }

        logger.debug("found ${searchResults.size()} AudioReleases before ${releasedBeforeDate.toString()} with at least ${minTrackCount} tracks")

        return convertToSearchResult(searchResults)
    }

    public MatchingReleases convertToSearchResult(List<AudioRelease> searchResults) {

        MatchingReleases matchingReleases = new MatchingReleases()

        List<MatchingReleases.ResultEntry> resultEntryList = searchResults.collect {
            new MatchingReleases.ResultEntry(name: it.name, trackCount: it.tracks.size())
        }

        matchingReleases.release = resultEntryList

        return matchingReleases
    }


}
