package com.tm.tvstreams

import channels.Channel
import channels.ChannelRepository

class EditChannelPresenter(
    private val channel: Channel,
    private val repository: ChannelRepository,
    private val view: EditChannelView
) {
    fun save(updatedChannel: Channel) {
        view.showLoadingView()
        repository.update(channel, updatedChannel) { updated(it) }
    }

    fun updated(success: Boolean) {
        view.dismiss()
    }
}
