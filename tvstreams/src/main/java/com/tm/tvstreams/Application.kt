package com.tm.tvstreams

import android.app.Application
import io.realm.Realm

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}