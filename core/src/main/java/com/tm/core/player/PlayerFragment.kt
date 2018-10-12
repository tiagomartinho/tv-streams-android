package com.tm.core.player

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.devbrackets.android.exomedia.listener.OnErrorListener
import com.devbrackets.android.exomedia.listener.OnPreparedListener
import com.devbrackets.android.exomedia.listener.VideoControlsButtonListener
import com.devbrackets.android.exomedia.ui.widget.VideoView

import com.tm.core.R
import java.lang.Exception

class PlayerFragment : Fragment(), OnPreparedListener, VideoControlsButtonListener, PlayerView, OnErrorListener {

    private var videoView: VideoView? = null
    private var presenter: PlayerPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val channels = it.getParcelableArrayList<ChannelPlayer>(PlayerActivity.CHANNELS)
            presenter = PlayerPresenter(channels, this)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter?.play()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        view.findViewById<Button>(R.id.retry_button).setOnClickListener {
            presenter?.play()
        }
        view.findViewById<Button>(R.id.next_button).setOnClickListener {
            presenter?.next()
        }
        view.findViewById<Button>(R.id.previous_button).setOnClickListener {
            presenter?.previous()
        }
        return view
    }

    override fun showVideoView() {
        val errorView = view?.findViewById<View>(R.id.error_view)
        errorView?.visibility = View.GONE
        videoView?.visibility = View.VISIBLE
        videoView = view?.findViewById<View>(R.id.video_view) as VideoView
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

    override fun play(channel: ChannelPlayer) {
        val uri = Uri.parse(channel.url)
        videoView?.setVideoURI(uri)
        videoView?.videoControls?.setTitle(channel.name)
    }

    override fun showPlaybackError() {
        val errorView = view?.findViewById<View>(R.id.error_view)
        errorView?.visibility = View.VISIBLE
        videoView?.visibility = View.GONE
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

    companion object {

        const val CHANNELS = "CHANNELS"

        @JvmStatic
        fun newInstance(channels: ArrayList<ChannelPlayer>) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(CHANNELS, channels)
                }
            }
    }
}
