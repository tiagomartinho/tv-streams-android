package channels

class PlaylistPresenter(
    private val view: AddPlaylistView,
    private val service: PlaylistService,
    private val repository: ChannelRepository
) {

    private var url: String = ""

    fun fetch(url: String) {
        if (url.isNullOrEmpty()) {
            view.showEmptyLinkView()
        } else {
            this.url = url
            view.showLoadingView()
            service.get(url) { receivedPlaylist(it) }
        }
    }

    fun receivedPlaylist(playlist: String) {
        val channels = PlaylistParser.parse(url, playlist)
        repository.add(channels) { savedPlaylist(it) }
    }

    fun savedPlaylist(success: Boolean) {
        view.hideLoadingView()
        if(success) {
            view.dismissView()
        } else {
            view.showErrorView()
        }
    }
}