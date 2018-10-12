package com.tm.core.player

import channels.Channel
import channels.channelPlayer

class ChannelListBuilder {
    companion object {
        fun build(channel: Channel?, channels: ArrayList<Channel>): Array<ChannelPlayer> {
            val index = channels.indexOf(channel)
            if (channel == null || channels.isEmpty() || channels.indexOf(channel) == -1) {
                return arrayOf()
            }
            val suffix = channels.subList(index, channels.count())
            val prefix = channels.subList(0, index)
            return (suffix + prefix).map { it.channelPlayer() }.toTypedArray()
        }
    }
}