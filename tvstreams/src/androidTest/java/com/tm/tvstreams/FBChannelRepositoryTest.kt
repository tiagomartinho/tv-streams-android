package com.tm.tvstreams

import channels.Channel
import junit.framework.Assert.assertEquals
import org.junit.Test

class FBChannelRepositoryTest {

    @Test
    fun addChannel() {
        val repository = FBChannelRepository()
        val channel = Channel(name = "Channel Name")

        repository.add(channel)

        repository.channels {
            assertEquals(1, it.count())
            assertEquals(channel, it.first())
        }
    }
}