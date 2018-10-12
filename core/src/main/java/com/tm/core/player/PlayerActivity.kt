package com.tm.core.player

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.devbrackets.android.exomedia.listener.VideoControlsVisibilityListener
import com.tm.core.R

class PlayerActivity : AppCompatActivity() {

    private val fullScreenListener = FullScreenListener()
    private lateinit var playerFragment: PlayerFragment

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("onKeyDown", keyCode.toString())
        when(keyCode) {
            KeyEvent.KEYCODE_DPAD_RIGHT,
            KeyEvent.KEYCODE_DPAD_DOWN_RIGHT,
            KeyEvent.KEYCODE_DPAD_DOWN,
            KeyEvent.KEYCODE_MEDIA_FAST_FORWARD,
            KeyEvent.KEYCODE_MEDIA_SKIP_FORWARD,
            KeyEvent.KEYCODE_MEDIA_STEP_FORWARD,
            KeyEvent.KEYCODE_MEDIA_NEXT,
            KeyEvent.ACTION_DOWN -> { playerFragment.presenter?.next(); return true }
            KeyEvent.KEYCODE_DPAD_LEFT,
            KeyEvent.KEYCODE_DPAD_UP_LEFT,
            KeyEvent.KEYCODE_DPAD_UP,
            KeyEvent.KEYCODE_MEDIA_REWIND,
            KeyEvent.KEYCODE_MEDIA_PREVIOUS,
            KeyEvent.KEYCODE_MEDIA_STEP_BACKWARD,
            KeyEvent.KEYCODE_MEDIA_SKIP_BACKWARD,
            KeyEvent.ACTION_UP -> { playerFragment.presenter?.previous(); return true }
            KeyEvent.KEYCODE_MEDIA_PLAY,
            KeyEvent.KEYCODE_MEDIA_PAUSE,
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE,
            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_ENTER -> { playPause(); return true }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun playPause() {
        if (playerFragment.videoView?.isPlaying!!) {
            playerFragment.videoView?.pause()
        } else {
            playerFragment.videoView?.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        if (savedInstanceState == null) {
            playerFragment = PlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(PlayerFragment.CHANNELS,
                        intent.getParcelableArrayListExtra(PlayerFragment.CHANNELS))
                }
            }
            playerFragment.setVisibilityListener(ControlsVisibilityListener())
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, playerFragment)
                .commit()
        }
        initUiFlags()
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
                playerFragment.videoView?.showControls()
            }
        }
    }

    internal inner class ControlsVisibilityListener : VideoControlsVisibilityListener {
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