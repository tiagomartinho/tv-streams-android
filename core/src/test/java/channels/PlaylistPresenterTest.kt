package channels

import org.junit.Test
import org.mockito.Mockito
import org.mockito.internal.verification.VerificationModeFactory

class PlaylistPresenterTest {

    private val view: AddPlaylistView = Mockito.mock(AddPlaylistView::class.java)
    private val service: PlaylistService = Mockito.mock(PlaylistService::class.java)

    @Test
    fun show_loading_view_when_fetching_playlist() {
        val url = "some url"
        val presenter = PlaylistPresenter(view, service)

        presenter.fetch(url)

        Mockito.verify(view, VerificationModeFactory.times(1)).showLoadingView()
    }

    @Test
    fun delegate_to_playlist_service_when_fetching_playlist() {
        val url = "some url"
        val presenter = PlaylistPresenter(view, service)

        presenter.fetch(url)

        Mockito.verify(service, VerificationModeFactory.times(1)).get(url)
    }
}