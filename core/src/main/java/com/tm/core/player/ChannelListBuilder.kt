package com.tm.core.player

import channels.Channel
import channels.channelPlayer

class ChannelListBuilder {
    companion object {
        fun build(channel: Channel?, channels: ArrayList<Channel>): Array<ChannelPlayer> {
            if(channel == null || channels.isEmpty()) {
                return arrayOf()
            }
            return channels.map { it.channelPlayer() }.toTypedArray()
        }
    }
}
