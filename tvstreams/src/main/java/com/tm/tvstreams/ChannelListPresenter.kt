package com.tm.tvstreams

import channels.Channel
import com.tm.tvstreams.ChannelListMode.*

class ChannelListPresenter(private val view: ChannelListView) {

    var mode = NORMAL

    fun start() {
        view.showLoadingView()
    }

    fun setChannels(channels: ArrayList<Channel>) {
        view.hideLoadingView()
        if (channels.isEmpty()) {
            view.showEmptyChannelsView()
        } else {
            view.showChannelsView(channels)
        }
    }

    fun select(channel: Channel) {
        when(mode) {
            NORMAL -> {
                view.showPlayerView(channel)
            }
            EDIT -> {
                view.showEditChannelView(channel)
            }
        }
    }

    fun deleteChannels() {

    }
}

enum class ChannelListMode {
    NORMAL, DELETE, EDIT
}