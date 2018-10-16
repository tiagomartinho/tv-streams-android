package channels

import junit.framework.Assert.assertEquals
import org.junit.Test

class PlaylistParserTest {

    @Test
    fun save_metadata() {
        val content = PlaylistParserTest::class.java.getResource("/list.m3u").readText()
        val metadata =
            "#EXTINF:-1 tvg-id=\"0500\" group-title=\"PortuguÃªs\" tvg-logo=\"0500.png\",[COLOR orange] RTP 1[/COLOR]"

        val channels = PlaylistParser.parse("", content)

        assertEquals(metadata, channels[1].metadata)
    }

    @Test
    fun save_source() {
        val content = PlaylistParserTest::class.java.getResource("/list.m3u").readText()
        val source = "some source"

        val channels = PlaylistParser.parse(source, content)

        assertEquals(source, channels[1].source)
    }

    @Test
    fun extract_all_channels_from_list() {
        val secondLink = "http://wownet.ro/str/0500.m3u8"
        val secondName = "RTP 1"
        val thirdLink = "https://9nowlivehls-i.akamaihd.net/hls/live/226644/ch9melprd/master.m3u8"
        val thirdName = "Channel9Mel"
        val lastLink = "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"
        val content = PlaylistParserTest::class.java.getResource("/list.m3u").readText()

        val channels = PlaylistParser.parse(content, content)

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

        val channels = PlaylistParser.parse(content, content)

        assertEquals(2, channels.count())
        assertEquals("http://02e4.vp9.tv/chn/btsu1/v.m3u8", channels[0].link)
        assertEquals("BT SPORT 1 HD", channels[0].name)
        assertEquals(
            "http://203.162.121.230:1935/live/model_channel/index.m3u8?securetoken=#ed%h0#w@1",
            channels[1].link
        )
        assertEquals("MODEL", channels[1].name)
    }

    @Test
    fun rmtp() {
        val content = PlaylistParserTest::class.java.getResource("/rmtp.m3u").readText()

        val channels = PlaylistParser.parse(content, content)

        assertEquals(1, channels.count())
        assertEquals("rtmp://62.210.139.136:1935/edge/_definst_/fvksu1sp51ijtyd?", channels[0].link)
        assertEquals("ANTENA 3", channels[0].name)
    }

    @Test
    fun other_list() {
        val firstName = "BBC One"
        val firstLink = "http://vs-hls-uk-live.edgesuite.net/pool_4/live/bbc_one_london/bbc_one_london.isml/bbc_one_london-pa3%3d96000-video%3d1604000.norewind.m3u8"
        val lastName = "CBS Action"
        val lastLink = "http://live-997.ld5.edge.filmon.com/live/1952.low.stream/playlist.m3u8?id=0ad5aac39bb13fbee717efd6dab09ffdb6bd80bcbf26a97df6a23c44d4fafde478e964988e7b80db4fe469983d2004d958cdf8169e9367ee45a031489be37ae85a01f3c1f02e87c60025e9eadec3fcbe5a1383e9522ce718c33749ae82e1e0219aa87786355a30be4d4850aa6d23c78fc0fe6133f83efc812b8fdafa22d8d0385f7f92a61ef395db60fca4e04d23bb88b2f7fe7a082bd77fce61b47495a23ecd36937d5dbd7c132597c64fd263f0f377"

        val content = PlaylistParserTest::class.java.getResource("/other_list.m3u").readText()

        val channels = PlaylistParser.parse(content, content)

        assertEquals(24, channels.count())
        assertEquals(firstLink, channels.first().link)
        assertEquals(firstName, channels.first().name)
        assertEquals(lastLink, channels.last().link)
        assertEquals(lastName, channels.last().name)
    }
}