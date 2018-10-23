package com.tm.tvstreams

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import channels.Channel
import kotlinx.android.synthetic.main.activity_edit_channel.*

class EditChannelActivity : AppCompatActivity() {

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
    }
}
