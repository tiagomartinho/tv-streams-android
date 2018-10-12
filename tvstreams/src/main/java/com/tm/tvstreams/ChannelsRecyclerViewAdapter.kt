package com.tm.tvstreams

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import channels.Channel
import com.tm.tvstreams.ChannelListFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.text_view.view.*

class ChannelsRecyclerViewAdapter(
    private var channels: List<Channel>,
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ChannelsRecyclerViewAdapter.ViewHolder>() {

    private val clickListener: View.OnClickListener

    init {
        clickListener = View.OnClickListener { v ->
            val item = v.tag as Channel
            listener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = channels[position]
        holder.mContentView.text = item.link

        with(holder.mView) {
            tag = item
            setOnClickListener(clickListener)
        }
    }

    override fun getItemCount(): Int = channels.size

    fun set(channels: List<Channel>) {
        this.channels = channels
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.textView

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}