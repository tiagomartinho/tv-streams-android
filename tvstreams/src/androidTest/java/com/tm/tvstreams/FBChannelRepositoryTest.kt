package com.tm.tvstreams

import channels.Channel
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class FBChannelRepositoryTest {

    private val lock = CountDownLatch(1)

    @Test
    fun addChannel() {
        val repository = FBChannelRepository()
        val channel = Channel(name = "Channel Name")
        var channels = ArrayList<Channel>()

        repository.add(channel)

        repository.channels {
            channels = it as ArrayList<Channel>
            lock.countDown()
        }

        lock.await(2000, TimeUnit.MILLISECONDS)

        assertEquals(1, channels.count())
        assertEquals(channel, channels.first())
    }
}