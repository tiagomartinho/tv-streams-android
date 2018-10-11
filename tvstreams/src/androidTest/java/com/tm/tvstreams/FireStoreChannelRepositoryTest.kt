package com.tm.tvstreams

import channels.Channel
import channels.ChannelRepository
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class FireStoreChannelRepositoryTest {

    private val userID = "1234"
    private val lock = CountDownLatch(1)
    private val channel = Channel(name = "Channel Name")
    private val newChannel = Channel(name = "New Channel Name")
    private lateinit var repository: ChannelRepository

    @Before
    fun setUp() {
        repository = FireStoreChannelRepository(userID)
    }

    @Test
    fun addChannel() {
        repository.add(channel)

        assertEquals(channel.name, channels().first().name)
    }

    @Test
    fun deleteAllChannels() {
        val deleteAllLock = CountDownLatch(1)

        repository.deleteAll {
            deleteAllLock.countDown()
        }

        deleteAllLock.await(2000, TimeUnit.MILLISECONDS)
        assertEquals(0, channels().count())
    }

    private fun channels(): List<Channel> {
        var channels = ArrayList<Channel>()
        repository.channels {
            channels = it as ArrayList<Channel>
            lock.countDown()
        }
        lock.await(2000, TimeUnit.MILLISECONDS)
        return channels
    }
}