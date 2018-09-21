package de.markus_kuenstler.backend.datasource

import de.markus_kuenstler.backend.domain.AudioRelease
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest("xml.filename=classpath:worldofmusic_testdata.xml")
public class XMLAudioReleaseDataSourceTest {

    @Autowired
    private XMLAudioReleaseDataSource cut

    @Before
    public void testInitializeDataSource() {
        cut.initializeDataSource()
    }

    @Test
    public void testGetAudioReleases() {

        Collection<AudioRelease> releases = cut.getAudioReleases()

        Assertions.assertThat(releases).isNotNull()

        Assertions.assertThat(releases).isNotEmpty()

        Assertions.assertThat(releases.size()).is(10)

    }

    @Test
    public void testGetFormats() {
        Set expectedFormats = ['CD', 'DVD', 'CASSETTE', 'TRACK 8'] as Set
        Assertions.assertThat(expectedFormats == cut.getFormats())
    }

    @Test
    public void testGetAudioReleasesByName() {

        String releaseName = "Anthology"

        List<AudioRelease> results = cut.getAudioReleasesByName(releaseName)
        Assertions.assertThat(results.size() == 1)

        Assertions.assertThat(results.get(0).name.equals(releaseName))
    }

}
