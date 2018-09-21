package de.markus_kuenstler.backend.domain

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

import javax.xml.bind.annotation.XmlElement

@JacksonXmlRootElement(localName = 'matchingReleases')
class MatchingReleases {

    static class ResultEntry {

        @XmlElement
        String name


        @XmlElement
        int trackCount

    }

    @JacksonXmlElementWrapper(localName = 'release', useWrapping = false)
    public List<ResultEntry> release = new ArrayList<ResultEntry>();


}
