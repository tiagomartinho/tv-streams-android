package com.tm.core

interface PlayerView {
    fun play(channel: Channel)
    fun showPlaybackError()
    fun showVideoView()
}
