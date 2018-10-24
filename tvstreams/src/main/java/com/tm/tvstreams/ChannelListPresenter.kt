package com.tm.tvstreams

import channels.Channel
import channels.ChannelRepository
import com.tm.tvstreams.ChannelListMode.*

class ChannelListPresenter(private val repository: ChannelRepository, private val view: ChannelListView) {

    var mode = NORMAL

    private var channelsCache = ArrayList<Channel>()

    fun loadChannels() {
        view.showLoadingView()
        repository.addListener {
            channelsCache = ArrayList(it)
            showChannels()
        }
    }

    fun setChannels(channels: ArrayList<Channel>) {
        repository.add(channels) {}
        channelsCache = channels
        showChannels()
    }

    fun showChannels() {
        view.hideLoadingView()
        if (channelsCache.isEmpty()) {
            view.showEmptyChannelsView()
        } else {
            view.showChannelsView(channelsCache)
        }
    }

    fun select(channel: Channel) {
        when (mode) {
            NORMAL -> {
                view.showPlayerView(channel, channelsCache)
            }
            EDIT -> {
                view.showEditChannelView(channel)
            }
            DELETE -> {

            }
        }
    }

    fun deleteChannels() {
    }

    fun deleteAllChannels() {
        channelsCache = ArrayList()
        showChannels()
        repository.deleteAll {}
    }
}

enum class ChannelListMode {
    NORMAL, DELETE, EDIT
}