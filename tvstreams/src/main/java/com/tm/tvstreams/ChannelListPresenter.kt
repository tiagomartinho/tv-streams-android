package com.tm.tvstreams

import channels.Channel

class ChannelListPresenter(private val view: ChannelListView) {
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
        view.showPlayerView(channel)
    }
}
