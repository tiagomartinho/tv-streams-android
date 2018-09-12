package com.tm.core

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.internal.verification.VerificationModeFactory.times

class PlayerPresenterTest {

    private val view: PlayerView = mock(PlayerView::class.java)

    @Test
    fun `empty channel list`() {
        val channels = ArrayList<Channel>()
        val presenter = PlayerPresenter(channels, view)

        presenter.play()
    }

    @Test
    fun `plays first channel in the list`() {
        val first = Channel("firstName", "firstURL")
        val second = Channel("secondName", "secondURL")
        val channels = arrayListOf(first, second)
        val presenter = PlayerPresenter(channels, view)

        presenter.play()

        verify(view, times(1)).play(channel = first)
    }

    @Test
    fun `play next channel in list`() {
        val first = Channel("firstName", "firstURL")
        val second = Channel("secondName", "secondURL")
        val channels = arrayListOf(first, second)
        val presenter = PlayerPresenter(channels, view)
        presenter.play()

        presenter.next()

        verify(view, times(2)).play(channel = first)
    }

    @Test
    fun `play next channel loops list`() {
        val first = Channel("firstName", "firstURL")
        val second = Channel("secondName", "secondURL")
        val channels = arrayListOf(first, second)
        val presenter = PlayerPresenter(channels, view)
        presenter.play()
        presenter.next()

        presenter.next()

        verify(view, times(3)).play(channel = first)
    }

    @Test
    fun `play previous channel in list`() {
        val first = Channel("firstName", "firstURL")
        val second = Channel("secondName", "secondURL")
        val channels = arrayListOf(first, second)
        val presenter = PlayerPresenter(channels, view)
        presenter.play()

        presenter.previous()

        verify(view, times(2)).play(channel = first)
    }

    @Test
    fun `play previous channel loops list`() {
        val first = Channel("firstName", "firstURL")
        val second = Channel("secondName", "secondURL")
        val channels = arrayListOf(first, second)
        val presenter = PlayerPresenter(channels, view)
        presenter.play()
        presenter.previous()

        presenter.previous()

        verify(view, times(3)).play(channel = first)
    }
}