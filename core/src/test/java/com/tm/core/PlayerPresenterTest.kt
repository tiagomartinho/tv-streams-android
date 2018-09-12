package com.tm.core

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

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

        verify(view).play(channel = first)
    }
}