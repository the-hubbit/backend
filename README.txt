This test took me about 9h.

It's a spring boot application providing a REST interface.

It only consists of three domain classes: AudioRelease, Track and MatchingReleases.

I know there are better ways to format to the expected xml-format but did not
find the time anymore to look it up.



Upcoming features:

1. The new XML Structure will require a new implementation of the Interface AudioReleaseDataSource

2. New search parameters (like the number of CDs) can easily be added to the REST endpoint


Usage:


Start with Java 8 (in background):

nohup java -jar backend-0.0.4-SNAPSHOT.jar --xml.filename=worldofmusic.xml &


Search for releases before 1.1.2001 with more than 10 tracks

curl -H "Accept:text/xml" "http://localhost:8080/api/audioreleases?releasedBefore=01/01/2001&minTrackCount=11" > result.xml

