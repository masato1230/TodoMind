package com.jp_funda.todomind

import android.app.Application
import io.realm.Realm

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        // Set up Realm
        Realm.init(this)
    }
}