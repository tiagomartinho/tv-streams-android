package channels

import junit.framework.Assert.assertEquals
import org.junit.Test

class PlaylistParserTest {
    @Test fun extract_all_channels_from_list() {
        val content = PlaylistParserTest::class.java.getResource("/list.m3u").readText()

        val channels = PlaylistParser.parse(content)

        assertEquals(10, channels.count())
    }
}