package com.tm.tvstreams

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import channels.Channel
import kotlinx.android.synthetic.main.activity_edit_channel.*
import user.SharedPreferencesUserRepository

class EditChannelActivity : AppCompatActivity(), EditChannelView {

    private lateinit var presenter: EditChannelPresenter

    companion object {
        const val CHANNEL = "CHANNEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_channel)
        val channel = intent.getParcelableExtra<Channel>(CHANNEL)
        name_text.setText(channel.name)
        link_text.setText(channel.link)
        cancel.setOnClickListener {
            finish()
        }
        save.setOnClickListener {
            presenter.save(
                Channel(
                    channel.source,
                    channel.metadata,
                    name_text.text.toString(),
                    link_text.text.toString()
                )
            )
        }
        val userID = SharedPreferencesUserRepository(this).load().id ?: ""
        val repository = FBChannelRepository(userID)
        presenter = EditChannelPresenter(channel, repository, this)
    }

    override fun dismiss() {
        finish()
    }

    override fun showLoadingView() {
        save.visibility = GONE
        editChannelProgressBar.visibility = VISIBLE
    }
}
