package com.tm.tvstreams

import channels.Channel
import channels.ChannelRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.Before
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
    fun addChannels() {
        val channels = ArrayList<Channel>()
        channels.add(channel)
        channels.add(newChannel)

        repository.add(channels)

        assert(channels().contains(channel))
        assert(channels().contains(newChannel))
    }

    @Test
    fun addChannel() {
        repository.add(channel)

        assertEquals(channel.name, channels().first().name)
    }

    @Test
    fun deleteChannel() {
        val deleteLock = CountDownLatch(1)

        repository.delete(channel) {
            deleteLock.countDown()
        }

        deleteLock.await(2000, TimeUnit.MILLISECONDS)
        assertFalse(channels().contains(channel))
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