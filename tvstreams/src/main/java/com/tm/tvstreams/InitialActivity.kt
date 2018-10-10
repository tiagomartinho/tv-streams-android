package com.tm.tvstreams

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import user.SharedPreferencesUserRepository

class InitialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        val userRepository = SharedPreferencesUserRepository(this)
        userRepository.load {
            if(it?.id.isNullOrEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent, null)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent, null)
            }
        }
    }
}
