package com.tm.tvstreams

import channels.Channel
import channels.ChannelRepository
import com.tm.tvstreams.ChannelListMode.*

class ChannelListPresenter(private val repository: ChannelRepository, private val view: ChannelListView) {

    private var mode = NORMAL
    private var channelsCache = ArrayList<Channel>()
    private var channelsToDelete = ArrayList<Channel>()

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
                if (channelsToDelete.contains(channel)) {
                    channelsToDelete.remove(channel)
                } else {
                    channelsToDelete.add(channel)
                }
            }
        }
    }

    fun deleteChannels() {
        if(!channelsToDelete.isEmpty()) {
            repository.delete(channelsToDelete) {}
            channelsCache.removeAll(channelsToDelete)
            channelsToDelete = ArrayList()
            showChannels()
        }
    }

    fun deleteAllChannels() {
        channelsCache = ArrayList()
        showChannels()
        repository.deleteAll {}
    }

    fun setDeleteMode() {
        mode = DELETE
        view.showDeleteMode()
    }

    fun setNormalMode() {
        mode = NORMAL
        view.showNormalMode()
    }

    fun setEditMode() {
        mode = EDIT
        view.showEditMode()
    }
}

enum class ChannelListMode {
    NORMAL, DELETE, EDIT
}