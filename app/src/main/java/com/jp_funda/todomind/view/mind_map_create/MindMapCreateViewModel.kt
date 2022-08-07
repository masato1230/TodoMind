package com.jp_funda.todomind.view.mind_map_create

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.database.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.database.repositories.task.entity.Task
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.domain.use_cases.mind_map.GetMindMapUseCase
import com.jp_funda.todomind.domain.use_cases.mind_map.UpdateMindMapUseCase
import com.jp_funda.todomind.domain.use_cases.ogp.GetOgpUseCase
import com.jp_funda.todomind.domain.use_cases.ogp.entity.OpenGraphResult
import com.jp_funda.todomind.domain.use_cases.task.GetTasksInAMindMapUseCase
import com.jp_funda.todomind.domain.use_cases.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@HiltViewModel
open class MindMapCreateViewModel @Inject constructor(
    private val getMindMapUseCase: GetMindMapUseCase,
    private val updateMindMapUseCase: UpdateMindMapUseCase,
    private val getTasksInAMindMapUseCase: GetTasksInAMindMapUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val getOgpUseCase: GetOgpUseCase,
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

    /** Id of mind map. */
    private lateinit var mindMapId: UUID

    /** MindMap - initialize at Fragment's onCreate */
    lateinit var mindMap: MindMap

    /** Tasks - all tasks in mind map. initialize at Fragment's onCreate */
    var tasks: List<Task> = emptyList()

    fun setMindMapId(id: UUID) {
        mindMapId = id
    }

    fun initializeScale() {
        scale = if (settingsPreferences.getFloat(PreferenceKeys.DEFAULT_MIND_MAP_SCALE) < 0f) 1f
        else settingsPreferences.getFloat(PreferenceKeys.DEFAULT_MIND_MAP_SCALE)
    }

    /** Get new data and Refresh MapView and scale percentage text. */
    fun refreshView() {
        viewModelScope.launch(Dispatchers.IO) {
            // Delay for keep consistency between view and db
            delay(100)
            getMindMapUseCase(mindMapId)?.let {
                mindMap = it
                // Load all data from db which is needed for drawing selected mind map
                tasks = getTasksInAMindMapUseCase(mindMap)
                _isLoading.postValue(false)
                _updateCount.postValue(_updateCount.value?.plus(1))
            }
        }
    }

    fun setScale(scale: Float) {
        if (this.scale != scale) {
            this.scale = scale
            _updateCount.postValue(_updateCount.value?.plus(1))
        }
    }

    fun getScale(): Float {
        return scale
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
        viewModelScope.launch(Dispatchers.IO) {
            val ogpResult = getOgpUseCase(siteUrl)
            ogpResult?.let(onSuccess) ?: run(onError)
        }
    }
}