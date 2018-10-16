package channels

import junit.framework.Assert.assertEquals
import org.junit.Test

class PlaylistParserTest {

    val secondLink = "http://wownet.ro/str/0500.m3u8"
    val secondName = "RTP 1"
    val thirdLink = "https://9nowlivehls-i.akamaihd.net/hls/live/226644/ch9melprd/master.m3u8"
    val thirdName = "Channel9Mel"
    val lastLink = "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"
    
    @Test fun extract_all_channels_from_list() {
        val content = PlaylistParserTest::class.java.getResource("/list.m3u").readText()

        val channels = PlaylistParser.parse(content)

        assertEquals(10, channels.count())
        assertEquals(secondLink,channels[1].link)
        assertEquals(secondName,channels[1].name)
        assertEquals(thirdLink,channels[2].link)
        assertEquals(thirdName,channels[2].name)
        assertEquals(lastLink,channels.last().link)
    }
}