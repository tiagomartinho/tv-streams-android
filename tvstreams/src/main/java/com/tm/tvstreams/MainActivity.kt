package com.tm.tvstreams

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tm.core.player.ChannelPlayer
import com.tm.core.player.FullScreenPlayerActivity
import com.tm.core.player.PlayerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, FullScreenPlayerActivity::class.java)
        val channels = ArrayList<ChannelPlayer>()
        channels.add(ChannelPlayer("A", "https://video-dev.github.io/streams/x36xhzz/x36xhzz.m3u8"))
        channels.add(ChannelPlayer("Fail", "https://something.com"))
        intent.putExtra(PlayerActivity.CHANNELS, channels)
        startActivity(intent, null)
    }
}
