package channels

import com.nhaarman.mockitokotlin2.any
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.internal.verification.VerificationModeFactory

class PlaylistPresenterTest {

    @Test
    fun write_channels_to_repository() {
        val channel = Channel("a","b","name", "http://link")

        presenter.receivedPlaylist(channel.name + "," + channel.link)

        verify(repository, VerificationModeFactory.times(1)).add(anyList(), any())
    }

    @Test
    fun show_loading_view_when_fetching_playlist() {
        presenter.fetch("some url")

        verify(view, VerificationModeFactory.times(1)).showLoadingView()
    }

    @Test
    fun delegate_to_playlist_service_when_fetching_playlist() {
        presenter.fetch("some url")

        verify(service, VerificationModeFactory.times(1)).get(any(),callback = any())
    }

    @Test
    fun show_empty_link_view() {
        presenter.fetch("")

        verify(view, VerificationModeFactory.times(1)).showEmptyLinkView()
    }

    @Test
    fun hide_loading_view_after_fetching_playlist() {
        presenter.savedPlaylist(false)

        verify(view, VerificationModeFactory.times(1)).hideLoadingView()
    }

    @Test
    fun dismiss_view_if_succeeds() {
        presenter.savedPlaylist(true)

        verify(view, VerificationModeFactory.times(1)).dismissView()
    }

    @Test
    fun show_error_if_save_fails() {
        presenter.savedPlaylist(false)

        verify(view, VerificationModeFactory.times(1)).showErrorView()
    }

    private val view: AddPlaylistView = mock(AddPlaylistView::class.java)
    private val service: PlaylistService = mock(PlaylistService::class.java)
    private val repository: ChannelRepository = mock(ChannelRepository::class.java)
    private lateinit var presenter: PlaylistPresenter
    @Before
    fun setUp() {
        presenter = PlaylistPresenter(view, service, repository)
    }
}