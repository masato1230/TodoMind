package com.jp_funda.todomind.view.mind_map_create

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.ogp.entity.OpenGraphResult
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.domain.use_cases.mind_map.UpdateMindMapUseCase
import com.jp_funda.todomind.domain.use_cases.task.GetTasksInAMindMapUseCase
import com.jp_funda.todomind.domain.use_cases.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@HiltViewModel
open class MindMapCreateViewModel @Inject constructor(
    private val updateMindMapUseCase: UpdateMindMapUseCase,
    private val getTasksInAMindMapUseCase: GetTasksInAMindMapUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
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
    private var scale = 1f

    /** MindMap - initialize at Fragment's onCreate */
    lateinit var mindMap: MindMap

    /** Tasks - all tasks in mind map. initialize at Fragment's onCreate */
    var tasks: List<Task> = emptyList()

    private val disposables = CompositeDisposable()

    fun initializeScale() {
        scale = if (settingsPreferences.getFloat(PreferenceKeys.DEFAULT_MIND_MAP_SCALE) < 0f) 1f
        else settingsPreferences.getFloat(PreferenceKeys.DEFAULT_MIND_MAP_SCALE)
    }

    /** Refresh MapView and scale percentage text */
    fun refreshView() { // TODO rename
        loadTaskData()
    }

    fun setScale(scale: Float) {
        this.scale = scale
        refreshView()
    }

    fun getScale(): Float {
        return scale
    }

    /** Load all data from db which is needed for drawing selected mind map */
    private fun loadTaskData() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            tasks = getTasksInAMindMapUseCase(mindMap)
            _isLoading.postValue(false)
            _updateCount.postValue(_updateCount.value?.plus(1))
        }
    }

    /** Update mindMap data in DB */
    fun updateMindMap(mindMap: MindMap) {
        this.mindMap = mindMap
        viewModelScope.launch(Dispatchers.IO) {
            updateMindMapUseCase(mindMap)
        }
    }

    /** Update task data in DB */
    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTaskUseCase(task)
        }
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

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}