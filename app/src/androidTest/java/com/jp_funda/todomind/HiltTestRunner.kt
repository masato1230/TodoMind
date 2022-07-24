package com.jp_funda.todomind

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication
import io.realm.Realm
import io.realm.RealmConfiguration

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context,
    ): Application {
        // Set up in memory realm
        Realm.init(context)
        RealmConfiguration.Builder().inMemory().name("test-realm").build()
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}