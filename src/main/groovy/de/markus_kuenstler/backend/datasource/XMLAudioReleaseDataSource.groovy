package de.markus_kuenstler.backend.datasource

import de.markus_kuenstler.backend.domain.AudioRelease
import de.markus_kuenstler.backend.domain.Track
import groovy.util.slurpersupport.NodeChild
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils

import javax.annotation.PostConstruct
import java.text.SimpleDateFormat

@Component
class XMLAudioReleaseDataSource implements AudioReleaseDataSource {

    Logger logger = LoggerFactory.getLogger(XMLAudioReleaseDataSource.class)

    @Value('${xml.filename}')
    private String sourceXMLFilename
    private List<AudioRelease> audioReleases

    // global set of formats
    private Set<String> globalFormatSet

    @Override
    @PostConstruct
    public initializeDataSource() throws IOException {

        globalFormatSet = new HashSet<String>();

        File xmlFile = ResourceUtils.getFile(sourceXMLFilename)

        XmlSlurper slurper = new XmlSlurper()
        def rootNode = slurper.parse(xmlFile)

        audioReleases = rootNode.record.collect { it -> createAudioReleaseFromNodeChild(it) }

        logger.debug("--- Found formats: ---")

        globalFormatSet.each { logger.debug(" \'${it}\'") }
    }

    private AudioRelease createAudioReleaseFromNodeChild(NodeChild nodeChild) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd")

        // create AudioRelease and set values
        def audioRelease = new AudioRelease()
        audioRelease.name = nodeChild.name.text()
        audioRelease.title = nodeChild.title.text()
        audioRelease.genre = nodeChild.genre.text()
        audioRelease.label = nodeChild.label.text()
        audioRelease.releaseDate = sdf.parse(nodeChild.releasedate.text())

        // set formats
        //make all formats uppercase to prevent multiple entries cause of tYpos
        String formatString = nodeChild.formats.text().toUpperCase()
        // split to list
        List<String> formatList = formatString.tokenize(',')
        // trim whitespace and create set of formats
        Set formatSet = formatList*.trim().toSet()
        audioRelease.formats.addAll(formatSet)

        // set tracklist
        nodeChild.tracklisting.track.each { it -> audioRelease.tracks.add(new Track(it.text())) }

        // add formats to global set so we can check for faulty entries later
        globalFormatSet.addAll(formatSet)

        //logger.debug(audioRelease.toString())

        return audioRelease
    }

    @Override
    Collection<AudioRelease> getAudioReleases() {

        audioReleases
    }

    @Override
    public Collection<AudioRelease> getAudioReleasesByName(String name) {

        List<AudioRelease> searchResults = audioReleases.findAll { ar -> name.equalsIgnoreCase(ar.name) }

        searchResults
    }

    public Set<String> getFormats() {
        globalFormatSet
    }
}
