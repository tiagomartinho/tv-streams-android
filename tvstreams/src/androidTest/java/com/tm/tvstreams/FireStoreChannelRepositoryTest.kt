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
        deleteAll()
    }

    @Test
    fun addChannels() {
        val addLock = CountDownLatch(1)
        val channels = arrayListOf(channel, newChannel)

        repository.add(channels) {
            addLock.countDown()
        }

        addLock.await(2000, TimeUnit.MILLISECONDS)
        val channelsInRepo = channels()
        assertEquals(newChannel.name, channelsInRepo.first().name)
        assertEquals(channel.name, channelsInRepo.last().name)
        assertEquals(2, channelsInRepo.count())
    }

    @Test
    fun addChannel() {
        addOneChannel()

        val channelsInRepo = channels()
        assertEquals(channel.name, channelsInRepo.first().name)
        assertEquals(1, channelsInRepo.count())
    }

    @Test
    fun deleteChannel() {
        addOneChannel()
        val deleteLock = CountDownLatch(1)

        repository.delete(channel) {
            deleteLock.countDown()
        }

        deleteLock.await(2000, TimeUnit.MILLISECONDS)
        val channelsInRepo = channels()
        assert(channelsInRepo.none { it.name == channel.name })
        assertEquals(0, channelsInRepo.count())
    }

    @Test
    fun deleteAllChannels() {
        deleteAll()

        assertEquals(0, channels().count())
    }

    @Test
    fun updateChannel() {
        addOneChannel()

        repository.update(channel, newChannel) {

        }

        val channelsInRepo = channels()
        assertEquals(newChannel.name, channelsInRepo.first().name)
        assertEquals(1, channelsInRepo.count())
    }

    private fun addOneChannel() {
        val addLock = CountDownLatch(1)

        repository.add(channel) {
            addLock.countDown()
        }

        addLock.await(2000, TimeUnit.MILLISECONDS)
    }

    private fun deleteAll() {
        val deleteAllLock = CountDownLatch(1)

        repository.deleteAll {
            deleteAllLock.countDown()
        }

        deleteAllLock.await(2000, TimeUnit.MILLISECONDS)
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