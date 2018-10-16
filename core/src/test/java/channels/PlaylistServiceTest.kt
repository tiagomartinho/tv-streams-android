package channels

import junit.framework.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test

class PlaylistServiceTest {

    @Ignore
    fun request() {
        val expected = PlaylistParserTest::class.java.getResource("/remote.m3u").readText().replace("\n", "")
        val url = "https://pastebin.com/raw/a6dMQ8n5"

        val result = PlaylistService.get(url).replace("\r\n", "")

        assertEquals(expected,result)
    }
}
