package com.tm.tvstreams

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import channels.Channel

class ChannelListFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_channel_list, container, false)
        if (view is RecyclerView) {
            with(view) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = ChannelsRecyclerViewAdapter(arrayListOf(), listener)
            }
            val decoration = SimpleDividerItemDecoration(Color.DKGRAY, 1)
            view.addItemDecoration(decoration)
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

    fun set(channels: List<Channel>) {
        val adapter = (view as? RecyclerView)?.adapter as? ChannelsRecyclerViewAdapter
        adapter?.set(channels)
        adapter?.notifyDataSetChanged()
    }

    interface OnListFragmentInteractionListener {
        fun onClickListFragmentInteraction(channel: Channel?)
        fun onLongClickListFragmentInteraction(channel: Channel?): Boolean
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChannelListFragment()
    }
}
