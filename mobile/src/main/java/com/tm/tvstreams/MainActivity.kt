package com.tm.tvstreams

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tm.core.PlayerPresenter
import com.tm.core.PlayerView

class MainActivity : AppCompatActivity(), PlayerView {

    override fun show(something: String) {
        Log.d("MainActivity", something)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val presenter = PlayerPresenter(view = this)
        presenter.play()
    }
}
