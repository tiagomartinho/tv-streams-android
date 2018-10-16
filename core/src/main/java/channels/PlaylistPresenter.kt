package channels

class PlaylistPresenter(private val view: AddPlaylistView, private val service: PlaylistService) {
    fun fetch(url: String) {
        if(url.isNullOrEmpty()) {
            view.showEmptyLinkView()
            return
        }
        view.showLoadingView()
        service.get(url) { receivedPlaylist(it) }
    }

    fun receivedPlaylist(playlist: String) {
        view.hideLoadingView()
    }
}
