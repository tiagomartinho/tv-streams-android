package channels

import org.junit.Assert.*
import org.junit.Test

class LinkParserTest {
    @Test
    fun extractEmptyLink() {
        assertEquals("", LinkParser.extractLink(""))
    }

    @Test
    fun extractLinkWithNormalSuffix() {
        val url = """
        http://live-997.ld5.edge.filmon.com/live/1952.low.stream/playlist.m3u8?
        id=0ad5aac39bb13fbee717efd6dab09ffdb6bd80bcbf26a97df6a23c44d4fafde478e
        964988e7b80db4fe469983d2004d958cdf8169e9367ee45a031489be37ae85a01f3c1f02e87
        c60025e9eadec3fcbe5a1383e9522ce718c33749ae82e1e0219aa87786355a30be4d4850aa6d
        23c78fc0fe6133f83efc812b8fdafa22d8d0385f7f92a61ef395db60fca4e04d23bb88b2f7fe7
        a082bd77fce61b47495a23ecd36937d5dbd7c132597c64fd263f0f377
        """
        val link = LinkParser.extractLink(url)
        assertEquals(url, link)
    }

    @Test
    fun extractLinkWithForwardedSuffix() {
        val url = "http://wownet.ro/str/0500.m3u8|X-Forwarded-For=81.17.18.50"
        val link = LinkParser.extractLink(url)
        assertEquals("http://wownet.ro/str/0500.m3u8", link)
    }
}