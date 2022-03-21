package com.jp_funda.todomind.di

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.shared_preferences.NotificationPreferences
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalMaterialApi
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSettingsPreferences(@ApplicationContext appContext: Context) =
        SettingsPreferences(appContext)

    @Provides
    @Singleton
    fun provideNotificationPreferences(@ApplicationContext appContext: Context) =
        NotificationPreferences(appContext)

    @Provides
    @Singleton
    fun provideTaskRepository(@ApplicationContext appContext: Context) =
        TaskRepository(appContext)

    @Provides
    @Singleton
    fun provideMindMapRepository() = MindMapRepository()

    @Provides
    @Singleton
    fun provideOgpRepository() = OgpRepository()
}