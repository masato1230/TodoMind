package com.jp_funda.todomind.di

import androidx.test.platform.app.InstrumentationRegistry
import com.jp_funda.todomind.data.repositories.jsoup.JsoupRepositoryImpl
import com.jp_funda.todomind.database.repositories.mind_map.MindMapRepositoryImpl
import com.jp_funda.todomind.database.repositories.task.TaskRepositoryImpl
import com.jp_funda.todomind.data.shared_preferences.NotificationPreferences
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.domain.repositories.JsoupRepository
import com.jp_funda.todomind.domain.repositories.MindMapRepository
import com.jp_funda.todomind.domain.repositories.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Provides
    @Singleton
    fun provideSettingsPreference() = SettingsPreferences(appContext)

    @Provides
    @Singleton
    fun provideNotificationPreferences() = NotificationPreferences(appContext)

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
        // TODO add test implementation
        return JsoupRepositoryImpl()
    }
}