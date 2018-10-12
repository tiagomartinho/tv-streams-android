package com.tm.tvstreams

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import channels.Channel
import android.view.View
import android.widget.Toast
import com.tm.tvstreams.ChannelListFragment.OnListFragmentInteractionListener
import user.SharedPreferencesUserRepository

class ChannelsActivity : AppCompatActivity(), OnListFragmentInteractionListener {

    private lateinit var channelListFragment: ChannelListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels)
        if (findViewById<View>(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return
            }
            channelListFragment = ChannelListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, channelListFragment).commit()
        }
        val userID = SharedPreferencesUserRepository(this).load().id
        val channelRepository = userID?.let { FireStoreChannelRepository(it) }
        channelRepository?.channels {
            channelListFragment.set(it)
        }
    }

    override fun onListFragmentInteraction(channel: Channel?) {
        Toast.makeText(applicationContext,"name", Toast.LENGTH_SHORT).show()
    }
}