package com.tm.core

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.*
import com.devbrackets.android.exomedia.listener.OnErrorListener
import com.devbrackets.android.exomedia.listener.OnPreparedListener
import com.devbrackets.android.exomedia.listener.VideoControlsButtonListener
import com.devbrackets.android.exomedia.ui.widget.VideoView
import java.lang.Exception

open class PlayerActivity : Activity(), OnPreparedListener, VideoControlsButtonListener, PlayerView, OnErrorListener {

    internal var videoView: VideoView? = null
    internal var presenter: PlayerPresenter? = null

    companion object {
        const val CHANNELS = "CHANNELS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupVideoView()
        val channels = intent.getParcelableArrayListExtra<Channel>(CHANNELS)
        presenter = PlayerPresenter(channels, this)
        presenter?.play()
    }

    private fun setupVideoView() {
        videoView = findViewById<View>(R.id.video_view) as VideoView
        videoView?.setOnPreparedListener(this)
        videoView?.setOnErrorListener(this)
        val videoControls = videoView!!.videoControls
        videoControls?.setPreviousButtonRemoved(false)
        videoControls?.setNextButtonRemoved(false)
        videoControls?.setPreviousButtonEnabled(true)
        videoControls?.setNextButtonEnabled(true)
        videoControls?.setButtonListener(this)
    }

    override fun onPrepared() {
        videoView?.start()
    }

    override fun onError(e: Exception?): Boolean {
        presenter?.playbackFailed()
        return true
    }

    override fun play(channel: Channel) {
        val uri = Uri.parse(channel.url)
        videoView?.setVideoURI(uri)
        videoView?.videoControls?.setTitle(channel.name)
    }

    override fun showPlaybackError() {
        val errorView = findViewById<View>(R.id.error_view)
        errorView.visibility = VISIBLE
        videoView?.visibility = GONE
    }

    override fun onNextClicked(): Boolean {
        presenter?.next()
        return true
    }

    override fun onPreviousClicked(): Boolean {
        presenter?.previous()
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
