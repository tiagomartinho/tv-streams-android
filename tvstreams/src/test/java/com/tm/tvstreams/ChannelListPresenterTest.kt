package com.tm.tvstreams

import channels.Channel
import channels.ChannelRepository
import com.nhaarman.mockitokotlin2.any
import com.tm.tvstreams.ChannelListMode.DELETE
import com.tm.tvstreams.ChannelListMode.EDIT
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class ChannelListPresenterTest {

    private val repository: ChannelRepository = Mockito.mock(ChannelRepository::class.java)
    private val view: ChannelListView = mock(ChannelListView::class.java)
    private lateinit var presenter: ChannelListPresenter

    @Before
    fun setUp() {
        presenter = ChannelListPresenter(repository, view)
    }

    @Test
    fun show_edit_channel_view_if_select_channel_while_in_edit_mode() {
        val channel = Channel()
        presenter.mode = EDIT

        presenter.select(channel)

        verify(view, times(1)).showEditChannelView(any())
    }

    @Test
    fun when_deleting_does_not_play_channel() {
        val channel = Channel()
        presenter.mode = DELETE

        presenter.select(channel)

        verify(view, times(0)).showPlayerView(any(), any())
    }

    @Test
    fun select_channel_plays_it() {
        val channel = Channel()

        presenter.select(channel)

        verify(view, times(1)).showPlayerView(any(), any())
    }

    @Test
    fun show_loading_at_start() {
        presenter.loadChannels()

        verify(view, times(1)).showLoadingView()
    }

    @Test
    fun hide_loading_when_channels_are_loaded() {
        presenter.showChannels()

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
