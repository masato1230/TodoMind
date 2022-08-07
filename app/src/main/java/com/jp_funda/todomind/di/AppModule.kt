package com.jp_funda.todomind.di

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.repositories.jsoup.JsoupRepository
import com.jp_funda.repositories.jsoup.JsoupRepositoryImpl
import com.jp_funda.repositories.mind_map.MindMapRepository
import com.jp_funda.repositories.mind_map.MindMapRepositoryImpl
import com.jp_funda.repositories.task.TaskRepository
import com.jp_funda.repositories.task.TaskRepositoryImpl
import com.jp_funda.todomind.sharedpreference.NotificationPreference
import com.jp_funda.todomind.sharedpreference.SettingsPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSettingsPreferences(@ApplicationContext appContext: Context) =
        SettingsPreference(appContext)

    @Provides
    @Singleton
    fun provideNotificationPreferences(@ApplicationContext appContext: Context) =
        NotificationPreference(appContext)

    @Provides
    @Singleton
    fun provideMindMapRepository(): MindMapRepository {
        return MindMapRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(): TaskRepository {
        return TaskRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideJsoupRepository(): JsoupRepository {
        return JsoupRepositoryImpl()
    }
}