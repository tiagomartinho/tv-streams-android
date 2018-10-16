package channels

class PlaylistPresenter(private val view: AddPlaylistView, private val service: PlaylistService) {
    fun fetch(url: String) {
        view.showLoadingView()
        service.get(url)
    }
}
