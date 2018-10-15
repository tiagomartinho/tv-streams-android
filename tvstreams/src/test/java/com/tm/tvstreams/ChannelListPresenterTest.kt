package com.tm.tvstreams

import channels.Channel
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class ChannelListPresenterTest {

    private val view: ChannelListView = mock(ChannelListView::class.java)

    @Test
    fun show_loading_at_start() {
        val presenter = ChannelListPresenter(view)

        presenter.start()

        verify(view, times(1)).showLoadingView()
    }

    @Test
    fun hide_loading_when_channels_are_loaded() {
        val presenter = ChannelListPresenter(view)

        presenter.setChannels(arrayListOf())

        verify(view, times(1)).hideLoadingView()
    }

    @Test
    fun show_empty_channels_view() {
        val presenter = ChannelListPresenter(view)

        presenter.setChannels(arrayListOf())

        verify(view, times(1)).showEmptyChannelsView()
    }

    @Test
    fun show_channels_list_view() {
        val presenter = ChannelListPresenter(view)
        val channels = arrayListOf(Channel("a", "b", "c", "d"))

        presenter.setChannels(channels)

        verify(view, times(1)).showChannelsView(channels)
    }
}
