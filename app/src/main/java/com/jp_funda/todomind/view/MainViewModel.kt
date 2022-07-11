package com.jp_funda.todomind.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.SampleData
import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.domain.use_cases.task.CreateTasksUseCase
import com.jp_funda.todomind.navigation.arguments.MindMapCreateArguments
import com.jp_funda.todomind.navigation.arguments.MindMapDetailArguments
import com.jp_funda.todomind.navigation.arguments.TaskDetailArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@HiltViewModel
class MainViewModel
@Inject constructor(
    private val mindMapRepository: MindMapRepository,
    private val createTasksUseCase: CreateTasksUseCase,
    private val settingsPreferences: SettingsPreferences,
) : ViewModel() {

    private val disposables = CompositeDisposable()

    fun addSampleData() {
        disposables.add(
            mindMapRepository.createMindMap(SampleData.mindMap)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewModelScope.launch(Dispatchers.IO) {
                        createTasksUseCase(SampleData.sampleTasks)
                        settingsPreferences.setBoolean(
                            PreferenceKeys.IS_NOT_FIRST_TIME_LAUNCH,
                            true,
                        )
                    }
                }
                .subscribe()
        )
    }

    /** Arguments for TaskDetailScreen - if editingTask is null, user is creating a new task */
    var taskDetailArguments = TaskDetailArguments(null)

    /** Arguments for MindMapDetailScreen - if editingMindMap is null, user is creating a new mind map. */
    lateinit var mindMapDetailArguments: MindMapDetailArguments

    /** Arguments for MindMapCreateScreen. */
    lateinit var mindMapCreateArguments: MindMapCreateArguments

    /** Currently deleted task - show at task list snackbar to restore the task */
    var currentlyDeletedTask: Task? = null

    override fun onCleared() {
        disposables.clear()
    }
}