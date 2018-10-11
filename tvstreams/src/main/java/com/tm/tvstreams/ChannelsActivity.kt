package com.tm.tvstreams

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import channels.Channel
import user.SharedPreferencesUserRepository
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.*

class ChannelsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewChannelsAdapter: ChannelsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels)

        viewManager = LinearLayoutManager(this)
        val userID = SharedPreferencesUserRepository(this).load().id
        val channelRepository = userID?.let { FireStoreChannelRepository(it) }
        channelRepository?.channels {
            val data = it.map { it.name }
            viewChannelsAdapter.set(data)
            viewChannelsAdapter.notifyDataSetChanged()
        }
        viewChannelsAdapter = ChannelsAdapter(arrayOf())

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewChannelsAdapter
        }

        val decoration = DividerItemDecoration(this, VERTICAL)
        recyclerView.addItemDecoration(decoration)

        channelRepository?.addListener {
            channelRepository?.channels {
                val data = it.map { it.name }
                viewChannelsAdapter.set(data)
                viewChannelsAdapter.notifyDataSetChanged()
            }
        }

        channelRepository?.add(Channel("source","meta","name","link")) {
            Log.d("ChannelsActivity", it.toString())
        }
    }
}

class ChannelsAdapter(private var data: Array<String>) :
        RecyclerView.Adapter<ChannelsAdapter.ViewHolder>() {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ChannelsAdapter.ViewHolder {
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.text_view, parent, false) as TextView
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position]
    }

    override fun getItemCount() = data.size

    fun set(data: List<String>) {
        this.data = data.toTypedArray()
    }
}