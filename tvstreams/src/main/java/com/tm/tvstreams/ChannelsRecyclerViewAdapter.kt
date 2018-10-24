package com.tm.tvstreams

import android.graphics.Color.*
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import channels.Channel
import com.tm.tvstreams.ChannelListFragment.OnListFragmentInteractionListener
import com.tm.tvstreams.ChannelListMode.DELETE
import com.tm.tvstreams.ChannelListMode.NORMAL
import kotlinx.android.synthetic.main.text_view.view.*

class ChannelsRecyclerViewAdapter(
    private var channels: List<Channel>,
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ChannelsRecyclerViewAdapter.ViewHolder>() {

    private val clickListener: View.OnClickListener
    private val longClickListener: View.OnLongClickListener
    private var mode = NORMAL

    init {
        clickListener = View.OnClickListener { v ->
            val item = v.tag as Channel
            if(mode == DELETE) {
                invertTextBackground(v)
            }
            listener?.onClickListFragmentInteraction(item)
        }
        longClickListener = View.OnLongClickListener { v ->
            val item = v.tag as Channel
            listener?.onLongClickListFragmentInteraction(item) ?: false
        }
    }

    private fun invertTextBackground(v: View) {
        val color = v.textView.textColors.defaultColor
        val redColor = v.context.resources.getColor(R.color.red)
        v.textView.setTextColor(if (color == redColor) WHITE else redColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = channels[position]
        holder.mContentView.text = item.name
        holder.mContentView.setTextColor(WHITE)
        with(holder.mView) {
            tag = item
            setOnClickListener(clickListener)
            setOnLongClickListener(longClickListener)
        }
    }

    override fun getItemCount(): Int = channels.size

    fun set(channels: List<Channel>) {
        this.channels = channels
    }

    fun setDeleteMode() {
        mode = DELETE
    }

    fun setNormalMode() {
        mode = NORMAL
        set(channels)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.textView

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}