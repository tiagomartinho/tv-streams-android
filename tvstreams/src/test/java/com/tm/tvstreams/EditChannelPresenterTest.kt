package com.tm.tvstreams

import channels.Channel
import channels.ChannelRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito

class EditChannelPresenterTest {

    private val repository: ChannelRepository = Mockito.mock(ChannelRepository::class.java)
    private val view: EditChannelView = Mockito.mock(EditChannelView::class.java)

    @Test
    fun `dismiss view if update succeeds`() {
        val presenter = EditChannelPresenter(Channel(), repository, view)

        presenter.updated(true)

        verify(view).dismiss()
    }

    @Test
    fun `update channel in repository`() {
        val channel = Channel()
        val presenter = EditChannelPresenter(channel, repository, view)

        val newChannel = Channel("a", "b", "c", "d")
        presenter.save(newChannel)

        verify(repository).update(any(), any(), any())
    }

    @Ignore
    fun `if channel is not modified do not save it`() {
        val channel = Channel()
        val presenter = EditChannelPresenter(channel, repository, view)

        presenter.save(channel)

        verifyNoMoreInteractions(repository)
    }
}