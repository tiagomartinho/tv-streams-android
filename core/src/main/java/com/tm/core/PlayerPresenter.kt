package com.tm.core

class PlayerPresenter(
        private val channels: ArrayList<Channel>,
        val view: PlayerView
) {

    private var currentChannelIndex = 0

    fun play() {
        if (channels.isEmpty()) { return }
        if (currentChannelIndex >= channels.count()) { return }
        view.play(channels[currentChannelIndex])
    }

    fun next() {
        if (channels.isEmpty()) { return }
        currentChannelIndex += 1
        currentChannelIndex %= channels.count()
        play()
    }

    fun previous() {
        if (channels.isEmpty()) { return }
        currentChannelIndex -= 1
        if (currentChannelIndex < 0) {
            currentChannelIndex = 0
        }
        play()
    }
}
