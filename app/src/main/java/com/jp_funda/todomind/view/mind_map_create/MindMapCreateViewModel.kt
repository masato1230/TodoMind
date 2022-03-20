package com.jp_funda.todomind.view.mind_map_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.ogp.entity.OpenGraphResult
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
open class MindMapCreateViewModel @Inject constructor(
    private val mindMapRepository: MindMapRepository,
    private val taskRepository: TaskRepository,
    private val ogpRepository: OgpRepository,
    private val settingsPreferences: SettingsPreferences,
) : ViewModel() {
    /** UpdateCount - count of view update. To update view count up this. */
    private val _updateCount = MutableLiveData(0)
    val updateCount: LiveData<Int> = _updateCount

    /** isLoading - flag for initial DB loading is finished */
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    /** Scale factor */
    private var scale =
        if (settingsPreferences.getFloat(PreferenceKeys.DEFAULT_MIND_MAP_SCALE) < 0) 1f
        else settingsPreferences.getFloat(PreferenceKeys.DEFAULT_MIND_MAP_SCALE)

    /** MindMap - initialize at Fragment's onCreate */
    lateinit var mindMap: MindMap

    /** Tasks - all tasks in mind map. initialize at Fragment's onCreate */
    var tasks: List<Task> = emptyList()

    private val disposables = CompositeDisposable()

    /** Refresh MapView and scale percentage text */
    fun refreshView() {
        loadTaskData {
            _updateCount.value = _updateCount.value!! + 1
        }
    }

    fun setScale(scale: Float) {
        this.scale = scale
        refreshView()
    }

    fun getScale(): Float {
        return scale
    }

    /** Load all data from db which is needed for drawing selected mind map */
    private fun loadTaskData(onSuccess: () -> Unit = {}) {
        disposables.add(
            taskRepository
                .getTasksInAMindMap(mindMap)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    tasks = it
                    _isLoading.value = false
                    onSuccess()
                }
                .subscribe({}, {
                    it.printStackTrace()
                })
        )
    }

    /** Update mindMap data in DB */
    fun updateMindMap(mindMap: MindMap) {
        this.mindMap = mindMap
        disposables.add(
            mindMapRepository.updateMindMap(mindMap)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    /** Update task data in DB */
    fun updateTask(task: Task) {
        disposables.add(
            taskRepository.updateTask(task)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    /** OGP */
    fun fetchOgp(
        siteUrl: String,
        onSuccess: (ogpResult: OpenGraphResult) -> Unit,
        onError: () -> Unit
    ) {
        disposables.add(
            ogpRepository.fetchOgp(siteUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    onSuccess(it)
                }
                .doOnError {
                    onError()
                }
                .subscribe({}, { it.printStackTrace() })
        )
    }

    /** Clear cached data */
    fun clearData() {
        tasks = emptyList()
        _isLoading.value = false
        scale = 1f
        disposables.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}