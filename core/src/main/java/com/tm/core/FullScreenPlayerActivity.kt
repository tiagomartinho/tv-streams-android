package com.tm.core

import android.os.Bundle
import android.view.View
import com.devbrackets.android.exomedia.listener.VideoControlsVisibilityListener

class FullScreenPlayerActivity : PlayerActivity() {

    private val fullScreenListener = FullScreenListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUiFlags()
        videoView?.videoControls?.setVisibilityListener(ControlsVisibilityListener())
    }

    override fun onDestroy() {
        super.onDestroy()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    private fun goFullscreen() {
        setUiFlags(true)
    }

    private fun exitFullscreen() {
        setUiFlags(false)
    }

    private fun initUiFlags() {
        window.decorView.systemUiVisibility = getStableUiFlags()
        window.decorView.setOnSystemUiVisibilityChangeListener(fullScreenListener)
    }

    private fun setUiFlags(fullscreen: Boolean) {
        window.decorView.systemUiVisibility = if (fullscreen) getFullscreenUiFlags() else getStableUiFlags()
    }

    private fun getFullscreenUiFlags(): Int {
        return (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun getStableUiFlags(): Int {
        return (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private inner class FullScreenListener : View.OnSystemUiVisibilityChangeListener {

        var lastVisibility = 0

        override fun onSystemUiVisibilityChange(visibility: Int) {
            lastVisibility = visibility
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                videoView!!.showControls()
            }
        }
    }

    private inner class ControlsVisibilityListener : VideoControlsVisibilityListener {
        override fun onControlsShown() {
            if (fullScreenListener.lastVisibility != View.SYSTEM_UI_FLAG_VISIBLE) {
                exitFullscreen()
            }
        }

        override fun onControlsHidden() {
            goFullscreen()
        }
    }
}