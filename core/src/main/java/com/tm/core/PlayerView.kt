package com.tm.core

interface PlayerView {
    fun play(channel: ChannelPlayer)
    fun showPlaybackError()
    fun showVideoView()
}
