package com.tm.core.player

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import com.tm.core.R

class PlayerActivity : AppCompatActivity() {

    private lateinit var playerFragment: PlayerFragment

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
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, playerFragment)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if(playerFragment.onKeyDown(keyCode)) {
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }
}