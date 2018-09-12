package com.tm.tvstreams

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tm.core.PlayerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(PlayerActivity.URL, "https://video-dev.github.io/streams/x36xhzz/x36xhzz.m3u8")
        intent.putExtra(PlayerActivity.NAME, "Big buck bunny")
        startActivity(intent, null)
    }
}
