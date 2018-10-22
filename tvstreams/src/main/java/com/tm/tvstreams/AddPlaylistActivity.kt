package com.tm.tvstreams

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.View.*
import channels.AddPlaylistView
import channels.OkHttpPlaylistService
import channels.PlaylistPresenter
import com.tm.tvstreams.R.string.unexpected_error
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
        save.visibility = GONE
        addPlaylistProgressBar.visibility = VISIBLE
    }

    override fun showEmptyLinkView() {
        showDialog(getString(R.string.valid_link))
    }

    override fun hideLoadingView() {
        save.visibility = VISIBLE
        addPlaylistProgressBar.visibility = GONE
    }

    override fun dismissView() {
        finish()
    }

    override fun showErrorView() {
        showDialog(getString(R.string.unexpected_error))
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
            .setTitle(getString(R.string.attention))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }
}
