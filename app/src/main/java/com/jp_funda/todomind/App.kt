package com.jp_funda.todomind

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm


@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        // Set up Realm
        Realm.init(this)
//        Realm.setDefaultConfiguration(RealmConfiguration.Builder().build())
//        Realm.deleteRealm(Realm.getDefaultConfiguration())
    }
}