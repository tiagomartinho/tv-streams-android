package com.tm.tvstreams

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import user.SharedPreferencesUserRepository

class InitialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        val userRepository = SharedPreferencesUserRepository(this)
        val user = userRepository.load()
        if (user.id.isNullOrEmpty()) {
            val clazz = LoginActivity::class.java
            val intent = Intent(this, clazz)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent, null)
            finish()
        } else {
            val clazz = ChannelsListActivity::class.java
            val intent = Intent(this, clazz)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent, null)
            finish()
        }
    }
}
