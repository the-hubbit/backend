package de.markus_kuenstler.backend.domain
/**
 * Domain Class AudioRelease
 *
 */
class AudioRelease {

    // not in data worldofmusic.xml but can be expected in next version
    String artist

    String title
    String name
    String label
    String genre
    Date releaseDate
    Set<String> formats = new HashSet<String>()
    List<Track> tracks = new ArrayList<Track>()

    @Override
    public String toString() {
        "${this.artist} - ${this.title} - ${this.name} [${this.releaseDate}] ( ${this.formats} )"
    }


}
