package com.tm.tvstreams

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import channels.Channel
import com.tm.core.player.ChannelListBuilder
import com.tm.core.player.PlayerActivity
import com.tm.core.player.PlayerFragment
import com.tm.tvstreams.R.id.add_sample_playlist
import kotlinx.android.synthetic.main.activity_channels_list.*
import kotlinx.android.synthetic.main.channels_list.*
import kotlinx.android.synthetic.main.empty_channels.*
import user.SharedPreferencesUserRepository
import android.support.v7.app.AlertDialog

class ChannelsListActivity : AppCompatActivity(), ChannelListFragment.OnListFragmentInteractionListener,
    ChannelListView {

    private var isFullScreen: Boolean = false
    private var twoPane: Boolean = false
    private lateinit var channelListFragment: ChannelListFragment
    private var channelRepository: FireStoreChannelRepository? = null
    private var playerFragment: PlayerFragment? = null
    private var channels = ArrayList<Channel>()
    private val presenter = ChannelListPresenter(this)

    override fun onDestroy() {
        super.onDestroy()
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_VISIBLE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (isFullScreen) {
            playerFragment?.onKeyDown(keyCode)?.let {
                return if (it) {
                    true
                } else {
                    super.onKeyDown(keyCode, event)
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels_list)
        setSupportActionBar(toolbar)
        toolbar.title = title
        fab.setOnClickListener {
            Snackbar.make(it, "Hello!", Snackbar.LENGTH_LONG).setAction("Action", null).show()
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
        channelRepository = userID?.let { FireStoreChannelRepository(it) }
        updateChannels(channelRepository)
        channelRepository?.addListener {
            updateChannels(channelRepository)
        }
        presenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_channels_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete_all -> {
            AlertDialog.Builder(this)
                .setTitle("Delete All Channels")
                .setMessage("Are you sure you want to delete all channels? Deleted channels cannot be recovered")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Delete")
                    { _, _ ->
                        channelRepository?.deleteAll {}
                        presenter.setChannels(arrayListOf())
                    }
                .setNegativeButton("Cancel", null).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateChannels(channelRepository: FireStoreChannelRepository?) {
        channelRepository?.channels {
            presenter.setChannels(ArrayList(it))
            channelListFragment?.set(it)
            channels = it as ArrayList<Channel>
        }
    }

    override fun showLoadingView() {
        channels_progress_bar.visibility = VISIBLE
    }

    override fun hideLoadingView() {
        channels_progress_bar.visibility = GONE
    }

    override fun showEmptyChannelsView() {
        empty_channels.visibility = VISIBLE
        add_sample_playlist.visibility = VISIBLE
        add_sample_playlist.setOnClickListener {
            addSamplePlaylist()
        }
    }

    fun addSamplePlaylist(): Boolean {
        val big = Channel("A", "B", "Big Buck Bunny", "https://video-dev.github.io/streams/x36xhzz/x36xhzz.m3u8")
        val sintel =
            Channel("A", "B", "Sintel", "https://download.blender.org/durian/trailer/sintel_trailer-1080p.mp4")
        val samplePlaylist = arrayListOf(big, sintel)
        channelRepository?.add(samplePlaylist) {}
        presenter.setChannels(samplePlaylist)
        return true
    }

    override fun showChannelsView(channels: ArrayList<Channel>) {
        empty_channels.visibility = GONE
        add_sample_playlist.visibility = GONE
    }

    override fun onListFragmentInteraction(channel: Channel?) {
        val channelsPlayer = ChannelListBuilder.build(channel, channels)
        if (twoPane) {
            playerFragment = PlayerFragment.newInstance(channelsPlayer)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, playerFragment)
                .commit()
            goFullscreen()
        } else {
            val intent = Intent(this, PlayerActivity::class.java).apply {
                putExtra(PlayerFragment.CHANNELS, channelsPlayer)
            }
            this.startActivity(intent)
        }
    }

    private fun goFullscreen() {
        isFullScreen = true
        supportActionBar?.hide()
        fragment_container.visibility = GONE
        fab.visibility = GONE
        setUiFlags(true)
    }

    private fun exitFullscreen() {
        isFullScreen = false
        supportActionBar?.show()
        fragment_container.visibility = VISIBLE
        fab.visibility = VISIBLE
        setUiFlags(false)
    }

    private fun setUiFlags(fullscreen: Boolean) {
        window.decorView.systemUiVisibility = if (fullscreen) getFullscreenUiFlags() else getStableUiFlags()
    }

    private fun getFullscreenUiFlags(): Int {
        return (SYSTEM_UI_FLAG_LOW_PROFILE
            or SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or SYSTEM_UI_FLAG_FULLSCREEN
            or SYSTEM_UI_FLAG_LAYOUT_STABLE
            or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun getStableUiFlags(): Int {
        return (SYSTEM_UI_FLAG_LAYOUT_STABLE
            or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
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
