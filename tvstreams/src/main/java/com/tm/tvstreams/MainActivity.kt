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
        channels.add(ChannelPlayer("B", "https://video-dev.github.io/streams/pts_shift/master.m3u8"))
        channels.add(ChannelPlayer("C", "https://video-dev.github.io/streams/dai-discontinuity-deltatre/manifest.m3u8"))
        channels.add(ChannelPlayer("D", "https://video-dev.github.io/streams/test_001/stream.m3u8"))
        channels.add(ChannelPlayer("E", "https://video-dev.github.io/streams/issue666/playlists/cisq0gim60007xzvi505emlxx.m3u8"))
        channels.add(ChannelPlayer("F", "https://video-dev.github.io/streams/bbbAES/playlists/sample_aes/index.m3u8"))
        channels.add(ChannelPlayer("G", "http://download.blender.org/peach/bigbuckbunny_movies/big_buck_bunny_720p_stereo.ogg"))
        channels.add(ChannelPlayer("Fail", "https://something.com"))
        intent.putExtra(PlayerActivity.CHANNELS, channels)
        startActivity(intent, null)
    }
}
