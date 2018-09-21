package de.markus_kuenstler.backend.datasource

import de.markus_kuenstler.backend.domain.AudioRelease

import javax.annotation.PostConstruct

interface AudioReleaseDataSource {

    @PostConstruct
    public initializeDataSource() throws IOException

    public Collection<AudioRelease> getAudioReleases()

    public Set<String> getFormats()

    public Collection<AudioRelease> getAudioReleasesByName(String name)


}