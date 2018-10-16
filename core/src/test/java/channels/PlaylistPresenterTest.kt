package channels

import com.nhaarman.mockitokotlin2.any
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.internal.verification.VerificationModeFactory

class PlaylistPresenterTest {

    @Test
    fun show_loading_view_when_fetching_playlist() {
        presenter.fetch("some url")

        Mockito.verify(view, VerificationModeFactory.times(1)).showLoadingView()
    }

    @Test
    fun delegate_to_playlist_service_when_fetching_playlist() {
        presenter.fetch("some url")

        Mockito.verify(service, VerificationModeFactory.times(1)).get(any(),callback = any())
    }

    @Test
    fun show_empty_link_view() {
        presenter.fetch("")

        Mockito.verify(view, VerificationModeFactory.times(1)).showEmptyLinkView()
    }

    @Test
    fun hide_loading_view_after_fetching_playlist() {
        presenter.receivedPlaylist("")

        Mockito.verify(view, VerificationModeFactory.times(1)).hideLoadingView()
    }

    private val view: AddPlaylistView = Mockito.mock(AddPlaylistView::class.java)
    private val service: PlaylistService = Mockito.mock(PlaylistService::class.java)
    private lateinit var presenter: PlaylistPresenter
    @Before
    fun setUp() {
        presenter = PlaylistPresenter(view, service)
    }
}