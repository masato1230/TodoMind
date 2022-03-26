package com.jp_funda.todomind.view.task_reminder

import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.view.task_detail.TaskEditableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltViewModel
class TaskReminderViewModel @Inject constructor(
    taskRepository: TaskRepository,
    ogpRepository: OgpRepository,
    settingsPreferences: SettingsPreferences,
) : TaskEditableViewModel(
    taskRepository = taskRepository,
    ogpRepository = ogpRepository,
    settingsPreferences = settingsPreferences
) {
    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    fun getTask(id: UUID) {
        taskRepository.getTask(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _loading.value = false
                setEditingTask(it)
            }
            .subscribe()
    }
}