package com.tm.core

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.devbrackets.android.exomedia.listener.OnPreparedListener
import com.devbrackets.android.exomedia.ui.widget.VideoView

open class PlayerActivity : Activity(), OnPreparedListener {

    internal var videoView: VideoView? = null

    companion object {
        const val CHANNELS = "CHANNELS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val channels = intent.getParcelableArrayListExtra<Channel>(CHANNELS)
        setupVideoView(channels)
    }

    private fun setupVideoView(channels: List<Channel>) {
        val channel = channels.firstOrNull()
        videoView = findViewById<View>(R.id.video_view) as VideoView
        videoView!!.setOnPreparedListener(this)
        videoView!!.setVideoURI(Uri.parse(channel?.url))
        val videoControls = videoView!!.videoControls
        videoControls?.setTitle(channel?.name)
        videoControls?.setPreviousButtonRemoved(false)
        videoControls?.setNextButtonRemoved(false)
        videoControls?.setPreviousButtonEnabled(true)
        videoControls?.setNextButtonEnabled(true)
    }

    override fun onPrepared() {
        videoView!!.start()
    }
}
