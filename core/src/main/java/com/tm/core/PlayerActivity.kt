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
        const val URL = "URL"
        const val NAME = "NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val url = intent.getStringExtra(URL)
        val name = intent.getStringExtra(NAME)
        setupVideoView(url, name)
    }

    private fun setupVideoView(url: String, title: String) {
        videoView = findViewById<View>(R.id.video_view) as VideoView
        videoView!!.setOnPreparedListener(this)
        videoView!!.setVideoURI(Uri.parse(url))
        val videoControls = videoView!!.videoControls
        videoControls?.setTitle(title)
        videoControls?.setPreviousButtonRemoved(false)
        videoControls?.setNextButtonRemoved(false)
        videoControls?.setPreviousButtonEnabled(true)
        videoControls?.setNextButtonEnabled(true)
    }

    override fun onPrepared() {
        videoView!!.start()
    }
}
