package com.tm.core.player

import channels.Channel
import channels.channelPlayer
import junit.framework.Assert.assertEquals
import org.junit.Test

class ChannelListBuilderTest {

    private val first = Channel("a","b","first","d")
    private val second = Channel("a","b","second","d")
    private val third = Channel("a","b","third","d")

    @Test
    fun `empty`() {
        assert(ChannelListBuilder.build(first, arrayListOf()).isEmpty())
        assert(ChannelListBuilder.build(null, arrayListOf()).isEmpty())
        assert(ChannelListBuilder.build(null, arrayListOf(first)).isEmpty())
    }

    @Test
    fun `single`() {
        assertEquals(first.channelPlayer(), ChannelListBuilder.build(first, arrayListOf(first)).first())
    }

    @Test
    fun `double`() {
        val channels = arrayListOf(first, second)
        assertEquals(first.channelPlayer(), ChannelListBuilder.build(first, channels).first())
        assertEquals(second.channelPlayer(), ChannelListBuilder.build(second, channels).first())
    }

    @Test
    fun `third`() {
        val channels = arrayListOf(first, second, third)
        assertEquals(first.channelPlayer(), ChannelListBuilder.build(first, channels).first())
        assertEquals(second.channelPlayer(), ChannelListBuilder.build(second, channels).first())
        assertEquals(third.channelPlayer(), ChannelListBuilder.build(third, channels).first())
    }
}