package com.tm.tvstreams

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LOW_PROFILE
import android.view.View.SYSTEM_UI_FLAG_VISIBLE
import android.view.View.VISIBLE
import channels.Channel
import com.tm.core.player.ChannelListBuilder
import com.tm.core.player.PlayerActivity
import com.tm.core.player.PlayerFragment
import kotlinx.android.synthetic.main.activity_channels_list.*
import kotlinx.android.synthetic.main.channels_list.*
import user.SharedPreferencesUserRepository

class ChannelsListActivity : AppCompatActivity(), ChannelListFragment.OnListFragmentInteractionListener,
    ChannelListView {

    private var isFullScreen: Boolean = false
    private var twoPane: Boolean = false
    private lateinit var channelListFragment: ChannelListFragment
    private var channelRepository: FBChannelRepository? = null
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
            val intent = Intent(this, AddPlaylistActivity::class.java)
            this.startActivity(intent)
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
        channelRepository = userID?.let { FBChannelRepository(it) }
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
        R.id.action_edit -> {
            presenter.startEditMode()
            startSupportActionMode(EditActionModeCallbacks.build(presenter))
            true
        }
        R.id.action_delete -> {
            true
        }
        R.id.action_logout -> {
            logout()
            true
        }
        R.id.action_delete_all -> {
            deleteAll()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAll() {
        AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
            .setTitle("Delete All Channels")
            .setMessage("Are you sure you want to delete all channels? Deleted channels cannot be recovered")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Delete")
            { _, _ ->
                channelRepository?.deleteAll {}
                presenter.setChannels(arrayListOf())
            }
            .setNegativeButton("Cancel", null).show()
    }

    private fun logout() {
        SharedPreferencesUserRepository(this).delete()
        val clazz = InitialActivity::class.java
        val intent = Intent(this, clazz)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent, null)
        finish()
    }

    private fun updateChannels(channelRepository: FBChannelRepository?) {
        channelRepository?.channels {
            presenter.setChannels(ArrayList(it))
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
        val linkBig = "https://video-dev.github.io/streams/x36xhzz/x36xhzz.m3u8"
        val nameBig = "Big Buck Bunny"
        val big = Channel(linkBig, nameBig, nameBig, linkBig)
        val linkSintel = "https://download.blender.org/durian/trailer/sintel_trailer-1080p.mp4"
        val nameSintel = "Sintel"
        val sintel =
            Channel(linkSintel, nameSintel, nameSintel, linkSintel)
        val samplePlaylist = arrayListOf(big, sintel)
        channelRepository?.add(samplePlaylist) {}
        presenter.setChannels(samplePlaylist)
        return true
    }

    override fun showChannelsView(channels: ArrayList<Channel>) {
        empty_channels.visibility = GONE
        add_sample_playlist.visibility = GONE
        channelListFragment?.set(channels)
        this.channels = channels
    }

    override fun onClickListFragmentInteraction(channel: Channel?) {
        channel?.let { presenter.select(it) }
    }

    override fun showPlayerView(channel: Channel) {
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

    override fun showEditChannelView(channel: Channel) {
        val intent = Intent(this, EditChannelActivity::class.java).apply {
            putExtra(EditChannelActivity.CHANNEL, channel)
        }
        startActivity(intent)
    }

    override fun onLongClickListFragmentInteraction(channel: Channel?): Boolean {
        return true
    }

    private fun goFullscreen() {
        isFullScreen = true
        supportActionBar?.hide()
        channels_list_view.visibility = GONE
        fab.visibility = GONE
        setUiFlags(true)
    }

    private fun exitFullscreen() {
        isFullScreen = false
        supportActionBar?.show()
        channels_list_view.visibility = VISIBLE
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