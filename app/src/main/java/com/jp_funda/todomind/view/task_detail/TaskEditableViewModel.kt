package com.jp_funda.todomind.view.task_detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.repositories.task.entity.NodeStyle
import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.sharedpreference.PreferenceKey
import com.jp_funda.todomind.sharedpreference.SettingsPreference
import com.jp_funda.todomind.use_case.ogp.GetOgpUseCase
import com.jp_funda.repositories.jsoup.entity.OpenGraphResult
import com.jp_funda.todomind.use_case.task.*
import com.jp_funda.todomind.util.UrlUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

/**
 * ViewModel for task editing or addTask
 * use setEditingTask() to switch to EditingMode
 */
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@HiltViewModel
open class TaskEditableViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var getTaskUseCase: GetTaskUseCase

    @Inject
    lateinit var createTasksUseCase: CreateTasksUseCase

    @Inject
    lateinit var updateTaskUseCase: UpdateTaskUseCase

    @Inject
    lateinit var getTasksInAMindMapUseCase: GetTasksInAMindMapUseCase

    @Inject
    lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @Inject
    lateinit var getOgpUseCase: GetOgpUseCase

    @Inject
    lateinit var settingsPreference: SettingsPreference

    protected var _task = MutableLiveData<Task?>(null)
    val task: LiveData<Task?> = _task
    var isEditing: Boolean = false
    val isShowOgpThumbnail: Boolean
        get() {
            return settingsPreference.getBoolean(PreferenceKey.IS_SHOW_OGP_THUMBNAIL)
        }

    // ogp
    private val _ogpResult = MutableLiveData<OpenGraphResult?>()
    val ogpResult: LiveData<OpenGraphResult?> = _ogpResult
    private var cachedSiteUrl: String? = null

    fun loadEditingTask(uuid: UUID) {
        isEditing = true
        viewModelScope.launch(Dispatchers.IO) {
            _task.postValue(getTaskUseCase(uuid))
        }
    }

    fun setNewEmptyTask() {
        _task.value = Task()
        isEditing = false
    }

    fun setEditingTask(editingTask: Task) {
        _task.postValue(editingTask)
        isEditing = true
    }

    fun setTitle(title: String) {
        _task.value!!.title = title
        notifyChangeToView()
    }

    fun setDescription(description: String) {
        _task.value!!.description = description
        notifyChangeToView()
    }

    fun setDate(localDate: LocalDate) {
        _task.value!!.dueDate =
            Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        notifyChangeToView()
    }

    fun resetDate() {
        _task.value!!.dueDate = null
        notifyChangeToView()
    }

    fun setTime(localTime: LocalTime) {
        if (_task.value!!.dueDate == null) {
            _task.value!!.dueDate = Date()
        }

        val instant = localTime.atDate(
            _task.value!!.dueDate!!.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        ).atZone(ZoneId.systemDefault()).toInstant()
        val date = Date.from(instant)
        _task.value!!.dueDate = date
        notifyChangeToView()
    }

    fun setColor(argb: Int) {
        _task.value!!.color = argb
        notifyChangeToView()
    }

    fun setStatus(statusEnum: TaskStatus) {
        _task.value!!.statusEnum = statusEnum
        notifyChangeToView()
    }

    fun setParentNode(parentNode: Task?) {
        _task.value!!.parentTask = parentNode
        _task.value!!.styleEnum =
            when (parentNode?.styleEnum?.ordinal) {
                null -> NodeStyle.HEADLINE_2
                in 0..NodeStyle.BODY_1.ordinal -> NodeStyle.values()[parentNode.styleEnum.ordinal + 1]
                else -> NodeStyle.BODY_2
            }
    }

    fun setX(x: Float) {
        _task.value!!.x = x
    }

    fun setY(y: Float) {
        _task.value!!.y = y
    }

    fun setStyle(styleEnum: NodeStyle) {
        _task.value!!.styleEnum = styleEnum
        notifyChangeToView()
    }

    open fun saveTask() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isEditing) {
                createTasksUseCase(listOf(_task.value!!))
            } else {
                updateTaskUseCase(_task.value!!)
            }
            clearData()
        }
    }

    fun deleteTask(task: Task) {
        if (isEditing) {
            viewModelScope.launch(Dispatchers.IO) {
                deleteTaskUseCase(task)
                clearData()
            }
        }
    }

    private fun notifyChangeToView() {
        _task.value = task.value?.copy() ?: Task()
    }

    // OGP
    private fun fetchOgp(siteUrl: String) {
        cachedSiteUrl = siteUrl // cash site url to reduce extra async task call
        viewModelScope.launch(Dispatchers.IO) {
            val ogpResult = getOgpUseCase(siteUrl)

            ogpResult?.let {
                _ogpResult.postValue(it)
            } ?: run {
                cachedSiteUrl = null
                _ogpResult.postValue(null)
            }
        }
    }

    fun extractUrlAndFetchOgp(text: String) {
        val siteUrl = UrlUtil.extractURLs(text).firstOrNull()

        if (siteUrl != null) {
            if (siteUrl != cachedSiteUrl) {
                fetchOgp(siteUrl)
            }
        } else {
            cachedSiteUrl = null
            _ogpResult.value = null
        }
    }

    // For ParentSelectDialog
    // Load task data
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    var tasksInSameMindMap = listOf<Task>()

    fun setParentTask(task: Task?) {
        _task.value!!.parentTask = task
        notifyChangeToView()
    }

    fun loadTasksInSameMindMap() {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            _task.value?.mindMap?.let {
                tasksInSameMindMap = getTasksInAMindMapUseCase(mindMap = it)
            }
            _isLoading.postValue(false)
        }
    }

    /** Clear editing/adding task data. */
    private fun clearData() {
        _task.postValue(Task())
    }
}