package com.tm.core

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.devbrackets.android.exomedia.listener.OnPreparedListener
import com.devbrackets.android.exomedia.listener.VideoControlsButtonListener
import com.devbrackets.android.exomedia.ui.widget.VideoView

open class PlayerActivity : Activity(), OnPreparedListener, VideoControlsButtonListener {

    internal var videoView: VideoView? = null
    private var channels: List<Channel>? = null

    companion object {
        const val CHANNELS = "CHANNELS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val channels = intent.getParcelableArrayListExtra<Channel>(CHANNELS)
        setupVideoView()
        play(channels.firstOrNull()!!)
        this.channels = channels
    }

    private fun setupVideoView() {
        videoView = findViewById<View>(R.id.video_view) as VideoView
        videoView!!.setOnPreparedListener(this)
        val videoControls = videoView!!.videoControls
        videoControls?.setPreviousButtonRemoved(false)
        videoControls?.setNextButtonRemoved(false)
        videoControls?.setPreviousButtonEnabled(true)
        videoControls?.setNextButtonEnabled(true)
        videoControls?.setButtonListener(this)
    }

    override fun onPrepared() {
        videoView!!.start()
    }

    fun play(channel: Channel) {
        videoView?.setVideoURI(Uri.parse(channel.url))
        videoView?.videoControls?.setTitle(channel.name)
    }

    override fun onNextClicked(): Boolean {
        val channel = channels?.lastOrNull()
        play(channel!!)
        return true
    }

    override fun onPreviousClicked(): Boolean {
        val channel = channels?.firstOrNull()
        play(channel!!)
        return true
    }

    override fun onPlayPauseClicked(): Boolean {
        return false
    }

    override fun onRewindClicked(): Boolean {
        return false
    }

    override fun onFastForwardClicked(): Boolean {
        return false
    }
}
