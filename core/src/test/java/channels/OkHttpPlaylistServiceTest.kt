package channels

import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class OkHttpPlaylistServiceTest {

    @Test
    fun `does not crash with empty string`() {
        val lock = CountDownLatch(1)

        val service = OkHttpPlaylistService()

        service.get("") {
            lock.countDown()
        }

        lock.await(2000, TimeUnit.MILLISECONDS)
    }

    @Test
    fun `does not crash with invalid string`() {
        val lock = CountDownLatch(1)

        val service = OkHttpPlaylistService()

        service.get("some channel name") {
            lock.countDown()
        }

        lock.await(2000, TimeUnit.MILLISECONDS)
    }
}