package com.tm.core

class PlayerPresenter(
        private val channels: ArrayList<Channel>,
        val view: PlayerView
) {

    fun play() {
        if (channels.isEmpty()) { return }
        view.play(channels.first())
    }
}
