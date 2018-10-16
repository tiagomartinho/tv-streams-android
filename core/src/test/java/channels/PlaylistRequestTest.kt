package channels

import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class PlaylistRequestTest {

    @Test
    fun request() {
        val expected = PlaylistParserTest::class.java.getResource("/remote.m3u").readText()
        val url = "https://pastebin.com/raw/a6dMQ8n5"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        val response = client.newCall(request).execute()
        val result = response.body()?.string() ?: ""
        assertEquals(expected.substring(0,500), result.substring(0,500))
    }
}