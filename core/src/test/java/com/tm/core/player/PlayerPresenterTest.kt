package com.tm.core.player

import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.internal.verification.VerificationModeFactory.times

class PlayerPresenterTest {

    private val view: PlayerView = mock(PlayerView::class.java)

    @Test
    fun `show video view once`() {
        val first = ChannelPlayer("firstName", "firstURL")
        val channels = arrayListOf(first)
        val presenter = PlayerPresenter(channels, view)

        presenter.play()
        presenter.play()
        presenter.next()
        presenter.previous()

        verify(view, times(1)).showVideoView()
    }

    @Test
    fun `empty channel list`() {
        val channels = ArrayList<ChannelPlayer>()
        val presenter = PlayerPresenter(channels, view)

        presenter.play()

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `plays first channel in the list`() {
        val first = ChannelPlayer("firstName", "firstURL")
        val second = ChannelPlayer("secondName", "secondURL")
        val channels = arrayListOf(first, second)
        val presenter = PlayerPresenter(channels, view)

        presenter.play()

        verify(view, times(1)).play(argThat { name == first.name })
    }

    @Test
    fun `play next channel in list`() {
        val first = ChannelPlayer("firstName", "firstURL")
        val second = ChannelPlayer("secondName", "secondURL")
        val channels = arrayListOf(first, second)
        val presenter = PlayerPresenter(channels, view)
        presenter.play()

        presenter.next()

        verify(view, times(1)).play(argThat { name == first.name })
        verify(view, times(1)).play(argThat { name == second.name })
    }

    @Test
    fun `play next channel loops list`() {
        val first = ChannelPlayer("firstName", "firstURL")
        val second = ChannelPlayer("secondName", "secondURL")
        val channels = arrayListOf(first, second)
        val presenter = PlayerPresenter(channels, view)
        presenter.play()
        presenter.next()

        presenter.next()

        verify(view, times(2)).play(argThat { name == first.name })
        verify(view, times(1)).play(argThat { name == second.name })
    }

    @Test
    fun `play previous channel in list`() {
        val first = ChannelPlayer("firstName", "firstURL")
        val second = ChannelPlayer("secondName", "secondURL")
        val channels = arrayListOf(first, second)
        val presenter = PlayerPresenter(channels, view)
        presenter.play()

        presenter.previous()

        verify(view, times(1)).play(argThat { name == first.name })
        verify(view, times(1)).play(argThat { name == second.name })
    }

    @Test
    fun `play previous channel loops list`() {
        val first = ChannelPlayer("firstName", "firstURL")
        val second = ChannelPlayer("secondName", "secondURL")
        val channels = arrayListOf(first, second)
        val presenter = PlayerPresenter(channels, view)
        presenter.play()
        presenter.previous()

        presenter.previous()

        verify(view, times(2)).play(argThat { name == first.name })
        verify(view, times(1)).play(argThat { name == second.name })
    }

    @Test
    fun `show error view if playback fails`() {
        val channels = ArrayList<ChannelPlayer>()
        val presenter = PlayerPresenter(channels, view)

        presenter.playbackFailed()

        verify(view).showPlaybackError()
    }

    @Test
    fun `retry shows video view`() {
        val first = ChannelPlayer("firstName", "firstURL")
        val channels = arrayListOf(first)
        val presenter = PlayerPresenter(channels, view)
        presenter.play()
        presenter.playbackFailed()

        presenter.play()

        verify(view, times(2)).showVideoView()
        verify(view, times(2)).play(argThat { name == first.name })
    }

    @Test
    fun `next shows video view if playback failed`() {
        val first = ChannelPlayer("firstName", "firstURL")
        val channels = arrayListOf(first)
        val presenter = PlayerPresenter(channels, view)
        presenter.play()
        presenter.playbackFailed()

        presenter.next()

        verify(view, times(2)).showVideoView()
        verify(view, times(2)).play(argThat { name == first.name })
    }
}