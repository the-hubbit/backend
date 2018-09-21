This test took me about 9h.

It's a spring boot application providing a REST interface.

It only consists of three domain class: AudioRelease, Track and MatchingReleases.





Upcoming features:

1. The new XML Structure will require a new implementation of the Interface AudioReleaseDataSource

2. New search parameters (like the number of CDs) can easily be addded to the REST endpoint



Usage:


Start with Java 8 (in background):

nohup java -jar backend-0.0.3-SNAPSHOT.jar --xml.filename=worldofmusic.xml &



Search for releases before 1.1.2001 with more than 10 tracks

curl -H "Accept:text/xml" "http://localhost:8080/api/audioreleases/findByProperties?releasedBefore=01/01/2001&minTrackCount=11" > result.xml

