package com.jp_funda.todomind.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.SampleData
import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@HiltViewModel
class MainViewModel
@Inject constructor(
    private val mindMapRepository: MindMapRepository,
    private val taskRepository: TaskRepository,
    private val settingsPreferences: SettingsPreferences,
) : ViewModel() {

    private val disposables = CompositeDisposable()

    fun addSampleData() {
        disposables.add(
            mindMapRepository.createMindMap(SampleData.mindMap)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    disposables.add(
                        taskRepository.createAll(SampleData.sampleTasks)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSuccess {
                                settingsPreferences.setBoolean(
                                    PreferenceKeys.IS_NOT_FIRST_TIME_LAUNCH,
                                    true
                                )
                            }
                            .subscribe()
                    )
                }
                .subscribe()
        )
    }

    /** Editing task at TaskDetailView - if this field is null, user is creating new task */
    var editingTask: Task? = null

    /** Editing mind map - pass mind map data between MindMap, MindMapDetail, MindMapCreate */
    var editingMindMap: MindMap? = null

    /** SelectedNode - node which is selected and showing options sheet.(when mind map is selected -> null) */
    var selectedNode: Task? = null

    /** Currently deleted task - show at task list snackbar to restore the task */
    var currentlyDeletedTask: Task? = null

    override fun onCleared() {
        disposables.clear()
    }
}