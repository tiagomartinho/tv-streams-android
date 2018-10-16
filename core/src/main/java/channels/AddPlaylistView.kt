package channels

interface AddPlaylistView {
    fun showLoadingView()
    fun showEmptyLinkView()
    fun hideLoadingView()
    fun dismissView()
    fun showErrorView()
}
