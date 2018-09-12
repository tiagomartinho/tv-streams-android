package com.tm.core

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.devbrackets.android.exomedia.listener.OnPreparedListener
import com.devbrackets.android.exomedia.ui.widget.VideoView

class PlayerActivity : Activity(), OnPreparedListener {

    private var videoView: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupVideoView()
    }

    private fun setupVideoView() {
        videoView = findViewById<View>(R.id.video_view) as VideoView
        videoView!!.setOnPreparedListener(this)
        videoView!!.setVideoURI(Uri.parse("https://video-dev.github.io/streams/x36xhzz/x36xhzz.m3u8"))
    }

    override fun onPrepared() {
        videoView!!.start()
    }

    companion object {
        const val SHARED_ELEMENT_NAME = "hero"
        const val MOVIE = "Movie"
    }
}
