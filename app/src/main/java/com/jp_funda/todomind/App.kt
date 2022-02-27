package com.jp_funda.todomind

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.realm.Realm
import io.realm.RealmConfiguration


@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        // Set up Realm
        Realm.init(this)
//        Realm.setDefaultConfiguration(RealmConfiguration.Builder().build())
//        Realm.deleteRealm(Realm.getDefaultConfiguration())

        // Rxjava error handling
        RxJavaPlugins.setErrorHandler { e: Throwable? -> e?.printStackTrace() }
    }
}