package com.tm.core.player

import android.view.View
import android.view.Window
import com.devbrackets.android.exomedia.listener.VideoControlsVisibilityListener
import com.devbrackets.android.exomedia.ui.widget.VideoView

class ControlsVisibilityListener(
    private val fullScreenListener: FullScreenListener,
    private val window: Window
) : VideoControlsVisibilityListener {

    override fun onControlsShown() {
        if (fullScreenListener.lastVisibility != View.SYSTEM_UI_FLAG_VISIBLE) {
            exitFullscreen()
        }
    }

    override fun onControlsHidden() {
        goFullscreen()
    }

    private fun goFullscreen() {
        setUiFlags(true)
    }

    private fun exitFullscreen() {
        setUiFlags(false)
    }

    fun initUiFlags() {
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
}

class FullScreenListener(private val videoView: VideoView) : View.OnSystemUiVisibilityChangeListener {

    internal var lastVisibility = 0

    override fun onSystemUiVisibilityChange(visibility: Int) {
        lastVisibility = visibility
        if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
            videoView.showControls()
        }
    }
}