package com.jp_funda.todomind.di

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepositoryImpl
import com.jp_funda.todomind.data.shared_preferences.NotificationPreferences
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.domain.use_cases.SetNextReminderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
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

    // TODO Delete
    @Provides
    @Singleton
    fun provideOldTaskRepository(@ApplicationContext appContext: Context) =
        TaskRepository(appContext)

    @Provides
    @Singleton
    fun provideTaskRepository(): com.jp_funda.todomind.domain.repositories.TaskRepository {
        return TaskRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSetNextReminderUseCase(@ApplicationContext appContext: Context) =
        SetNextReminderUseCase(appContext)
}