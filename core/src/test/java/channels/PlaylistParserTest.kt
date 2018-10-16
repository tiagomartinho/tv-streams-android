package channels

import junit.framework.Assert.assertEquals
import org.junit.Test

class PlaylistParserTest {

    @Test
    fun extract_all_channels_from_list() {
        val secondLink = "http://wownet.ro/str/0500.m3u8"
        val secondName = "RTP 1"
        val thirdLink = "https://9nowlivehls-i.akamaihd.net/hls/live/226644/ch9melprd/master.m3u8"
        val thirdName = "Channel9Mel"
        val lastLink = "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"
        val content = PlaylistParserTest::class.java.getResource("/list.m3u").readText()

        val channels = PlaylistParser.parse(content)

        assertEquals(10, channels.count())
        assertEquals(secondLink, channels[1].link)
        assertEquals(secondName, channels[1].name)
        assertEquals(thirdLink, channels[2].link)
        assertEquals(thirdName, channels[2].name)
        assertEquals(lastLink, channels.last().link)
    }

    @Test
    fun extract_from_csv_list() {
        val content = PlaylistParserTest::class.java.getResource("/csv.m3u").readText()

        val channels = PlaylistParser.parse(content)

        assertEquals(2, channels.count())
        assertEquals("http://02e4.vp9.tv/chn/btsu1/v.m3u8", channels[0].link)
        assertEquals("BT SPORT 1 HD", channels[0].name)
        assertEquals("http://203.162.121.230:1935/live/model_channel/index.m3u8?securetoken=#ed%h0#w@1", channels[1].link)
        assertEquals("MODEL", channels[1].name)
    }
}