package com.tm.tvstreams

import channels.Channel
import com.nhaarman.mockitokotlin2.any
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class ChannelListPresenterTest {

    private val view: ChannelListView = mock(ChannelListView::class.java)
    private lateinit var presenter: ChannelListPresenter

    @Before
    fun setUp() {
        presenter = ChannelListPresenter(view)
    }

    @Test
    fun show_edit_channel_view_if_select_channel_while_in_edit_mode() {
        val channel = Channel()

        presenter.startEditMode()
        presenter.select(channel)

        verify(view, times(1)).showEditChannelView(any())
    }

    @Test
    fun when_deleting_does_not_play_channel() {
        val channel = Channel()

        presenter.startDeleteMode()
        presenter.select(channel)

        verify(view, times(0)).showPlayerView(any())
    }

    @Test
    fun select_channel_plays_it() {
        val channel = Channel()

        presenter.select(channel)

        verify(view, times(1)).showPlayerView(any())
    }

    @Test
    fun show_loading_at_start() {
        presenter.start()

        verify(view, times(1)).showLoadingView()
    }

    @Test
    fun hide_loading_when_channels_are_loaded() {
        presenter.setChannels(arrayListOf())

        verify(view, times(1)).hideLoadingView()
    }

    @Test
    fun show_empty_channels_view() {
        presenter.setChannels(arrayListOf())

        verify(view, times(1)).showEmptyChannelsView()
    }

    @Test
    fun show_channels_list_view() {
        val channels = arrayListOf(Channel("a", "b", "c", "d"))

        presenter.setChannels(channels)

        verify(view, times(1)).showChannelsView(channels)
    }
}
