package de.markus_kuenstler.backend.controller

import de.markus_kuenstler.backend.domain.AudioRelease
import de.markus_kuenstler.backend.domain.MatchingReleases
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest("xml.filename=classpath:worldofmusic_testdata.xml")
class AudioReleaseRESTControllerTest {

    @Autowired
    private AudioReleaseRESTController cut


    @Test
    public void testConvertToSearchResult() {

        String releaseName = "Anthology"

        ArrayList<AudioRelease> audioReleases = cut.getAudioReleaseDataSource().getAudioReleasesByName(releaseName)

        MatchingReleases matchingReleases = cut.convertToSearchResult(audioReleases)

        Assertions.assertThat(matchingReleases.release).hasSize(1)

        Assertions.assertThat(matchingReleases.release.get(0)).hasFieldOrPropertyWithValue('name', releaseName)
    }


}
