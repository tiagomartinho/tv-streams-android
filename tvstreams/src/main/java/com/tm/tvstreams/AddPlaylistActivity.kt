package com.tm.tvstreams

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import channels.AddPlaylistView
import channels.OkHttpPlaylistService
import channels.PlaylistPresenter
import kotlinx.android.synthetic.main.activity_add_playlist.*
import user.SharedPreferencesUserRepository

class AddPlaylistActivity : AppCompatActivity(), AddPlaylistView {

    private lateinit var presenter: PlaylistPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_playlist)
        val userID = SharedPreferencesUserRepository(this).load().id ?: ""
        val repository = FBChannelRepository(userID = userID)
        presenter = PlaylistPresenter(view = this, service = OkHttpPlaylistService(), repository = repository)
        cancel.setOnClickListener {
            finish()
        }
        save.setOnClickListener {
            presenter.fetch(link_text.text.toString())
        }
    }

    override fun showLoadingView() {
        Log.d("AddPlaylistActivity","showLoadingView")
    }

    override fun showEmptyLinkView() {
        Log.d("AddPlaylistActivity","showEmptyLinkView")
    }

    override fun hideLoadingView() {
        Log.d("AddPlaylistActivity","hideLoadingView")
    }

    override fun dismissView() {
        finish()
    }

    override fun showErrorView() {
        Log.d("AddPlaylistActivity","showErrorView")
    }
}
