package com.tm.core

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
        setupVideoView(url)
        val name = intent.getStringExtra(NAME)
        Log.d("PlayerActivity", name)
    }

    private fun setupVideoView(url: String) {
        videoView = findViewById<View>(R.id.video_view) as VideoView
        videoView!!.setOnPreparedListener(this)
        videoView!!.setVideoURI(Uri.parse(url))
    }

    override fun onPrepared() {
        videoView!!.start()
    }
}
