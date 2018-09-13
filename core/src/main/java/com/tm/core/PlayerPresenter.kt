package com.tm.core

class PlayerPresenter(
        private val channels: ArrayList<Channel>,
        val view: PlayerView
) {

    private var currentChannelIndex = 0
    private var videoViewShown = false

    fun play() {
        if (channels.isEmpty()) { return }
        if (currentChannelIndex >= channels.count()) { return }
        if(!videoViewShown) {
            view.showVideoView()
            videoViewShown = true
        }
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
            currentChannelIndex = channels.count() - 1
        }
        play()
    }

    fun playbackFailed() {
        view.showPlaybackError()
    }
}
