package com.tm.tvstreams

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import channels.Channel
import com.tm.core.player.ChannelPlayer
import com.tm.core.player.PlayerActivity
import com.tm.core.player.PlayerFragment
import kotlinx.android.synthetic.main.activity_channels_list.*
import kotlinx.android.synthetic.main.channels_list.*
import user.SharedPreferencesUserRepository

class ChannelsListActivity : AppCompatActivity(), ChannelListFragment.OnListFragmentInteractionListener {

    private var twoPane: Boolean = false
    private lateinit var channelListFragment: ChannelListFragment
    private var playerFragment: PlayerFragment? = null
    private var channels = ArrayList<Channel>()

    override fun onDestroy() {
        super.onDestroy()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        playerFragment?.onKeyDown(keyCode)?.let {
            return if (it) {
                true
            } else {
                super.onKeyDown(keyCode, event)
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels_list)
        setSupportActionBar(toolbar)
        toolbar.title = title
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        twoPane = item_detail_container != null
        if (findViewById<View>(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return
            }
            channelListFragment = ChannelListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, channelListFragment)
                .commit()
        }
        val userID = SharedPreferencesUserRepository(this).load().id
        val channelRepository = userID?.let { FireStoreChannelRepository(it) }
        updateChannels(channelRepository)
        channelRepository?.addListener {
            updateChannels(channelRepository)
        }
    }

    private fun updateChannels(channelRepository: FireStoreChannelRepository?) {
        channelRepository?.channels {
            channelListFragment?.set(it)
            channels = it as ArrayList<Channel>
        }
    }

    override fun onListFragmentInteraction(channel: Channel?) {
        if (twoPane) {
            val channels = ArrayList<ChannelPlayer>()
            channel?.name?.let { ChannelPlayer(it, channel.link) }?.let { channels.add(it) }
            playerFragment = PlayerFragment.newInstance(channels)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, playerFragment)
                .commit()
            goFullscreen()
        } else {
            val intent = Intent(this, PlayerActivity::class.java).apply {
                val channels = ArrayList<ChannelPlayer>()
                channel?.name?.let { ChannelPlayer(it, channel.link) }?.let { channels.add(it) }
                putExtra(PlayerFragment.CHANNELS, channels)
            }
            this.startActivity(intent)
        }
    }

    private fun goFullscreen() {
        supportActionBar?.hide()
        fragment_container.visibility = GONE
        fab.visibility = GONE
        setUiFlags(true)
    }

    private fun exitFullscreen() {
        supportActionBar?.show()
        fragment_container.visibility = VISIBLE
        fab.visibility = VISIBLE
        setUiFlags(false)
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

    private var backButtonCount: Int = 0

    override fun onBackPressed() {
        if (backButtonCount >= 1) {
            backButtonCount = 0
            super.onBackPressed()
        } else {
            exitFullscreen()
            backButtonCount++
        }
    }
}