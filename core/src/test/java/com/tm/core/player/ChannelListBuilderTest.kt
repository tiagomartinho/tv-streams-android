package com.tm.core.player

import channels.Channel
import channels.channelPlayer
import junit.framework.Assert.assertEquals
import org.junit.Test

class ChannelListBuilderTest {

    val first = Channel("a","b","first","d")
    val second = Channel("a","b","second","d")
    val third = Channel("a","b","third","d")

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
}
//    func testDouble() {
//        XCTAssertEqual([first, second], ChannelListBuilder.build(channel: first, channels: [first, second]))
//        XCTAssertEqual([second, first], ChannelListBuilder.build(channel: second, channels: [first, second]))
//    }
//
//    func testThird() {
//        let channels = [first, second, third]
//        XCTAssertEqual([first, second, third], ChannelListBuilder.build(channel: first, channels: channels))
//        XCTAssertEqual([second, third, first], ChannelListBuilder.build(channel: second, channels: channels))
//        XCTAssertEqual([third, first, second], ChannelListBuilder.build(channel: third, channels: channels))
//    }
