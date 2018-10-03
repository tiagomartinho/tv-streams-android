package channels

import junit.framework.Assert.assertEquals
import org.junit.Test

class ChannelRepositoryTest {

    @Test
    fun `add channel`() {
        val repository = ChannelRepository()
        val channel = Channel()

        repository.add(channel)

        val channels = repository.channels()
//        assertEquals(1, channels.count())
//        assert(channels.contains(channel))
    }
}