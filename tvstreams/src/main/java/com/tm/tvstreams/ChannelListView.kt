package com.tm.tvstreams

import channels.Channel

interface ChannelListView {
    fun showLoadingView()
    fun hideLoadingView()
    fun showEmptyChannelsView()
    fun showChannelsView(channels: ArrayList<Channel>)
}