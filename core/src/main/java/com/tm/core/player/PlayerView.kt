package com.tm.core.player

interface PlayerView {
    fun play(channel: ChannelPlayer)
    fun showPlaybackError()
    fun showVideoView()
}
