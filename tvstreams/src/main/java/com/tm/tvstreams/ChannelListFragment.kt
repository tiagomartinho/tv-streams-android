package com.tm.tvstreams

import android.content.Context
import android.graphics.Color.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.*
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import channels.Channel
import android.support.v7.widget.helper.ItemTouchHelper

class ChannelListFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null
    private fun channelsRecyclerViewAdapter() = (view as? RecyclerView)?.adapter as? ChannelsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_channel_list, container, false)
        if (view is RecyclerView) {
            with(view) {
                setHasFixedSize(true)
                val layoutManager = LinearLayoutManager(context)
                layoutManager.orientation = VERTICAL
                this.layoutManager = layoutManager
                val adapter = ChannelsRecyclerViewAdapter(arrayListOf(), listener)
                this.adapter = adapter
                val callback = SimpleItemTouchHelperCallback(adapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(this)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun setDeleteMode() {
        channelsRecyclerViewAdapter()?.setDeleteMode()
    }

    fun setNormalMode() {
        channelsRecyclerViewAdapter()?.setNormalMode()
    }

    fun set(channels: List<Channel>) {
        val adapter = channelsRecyclerViewAdapter()
        adapter?.set(channels)
        adapter?.notifyDataSetChanged()
    }

    interface OnListFragmentInteractionListener {
        fun onClickListFragmentInteraction(channel: Channel?)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChannelListFragment()
    }
}