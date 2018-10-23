package com.tm.tvstreams

import channels.Channel
import com.tm.tvstreams.ChannelListMode.*

class ChannelListPresenter(private val view: ChannelListView) {

    private var mode = NORMAL

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

    fun startDeleteMode() {
        mode = DELETE
    }

    fun startEditMode() {
        mode = EDIT
    }

    fun stopEditMode() {
        mode = NORMAL
    }
}

enum class ChannelListMode {
    NORMAL, DELETE, EDIT
}