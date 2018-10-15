package channels

import org.junit.Assert.*
import org.junit.Test

class NameParserTest {
    @Test
    fun is_channel_if_contains_prefix() {
        assert(NameParser.isChannel("#EXTINF:-1"))
        assertFalse(NameParser.isChannel("some other thing"))
    }

    @Test
    fun extractChannelWithSimpleExtraInfo() {
        val name = "#EXTINF:-1 tvg-id=\"0500\" group-title=\"Ps\" tvg-logo=\"0500.png\",[COLOR orange] RTP 1[/COLOR]"
        assertEquals("RTP 1", NameParser.extract(name))
    }

    @Test
    fun extractChannelWithDoubleExtraInfo() {
        val name = "#EXTINF:-1 tvg-id=\"RTP 2\" tvg-logo=\"RTP2.png\" group-title=\"Canal\",[I][B]RTP 2[/B][/I]"
        assertEquals("RTP 2", NameParser.extract(name))
    }

    @Test
    fun extractChannelWithReverseExtraInfo() {
        val name = "] reverse ["
        assertEquals("] reverse [", NameParser.extract(name))
    }

    @Test
    fun extractChannelWithSpacedExtraInfo() {
        val name = " [I] a [B] RTP 2 [/B] a [/I] "
        assertEquals("a  RTP 2  a", NameParser.extract(name))
    }

    @Test
    fun extractChannelWithOnlyExtraInfo() {
        val name = " [asd] "
        assertEquals("", NameParser.extract(name))
    }

    @Test
    fun extractChannelWithEmptyInfo() {
        val name = ""
        assertEquals("", NameParser.extract(name))
    }
}