package com.jp_funda.todomind.view.task_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.ogp.entity.OpenGraphResult
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.util.UrlUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

/**
 * ViewModel for task editing or addTask
 * use setEditingTask() to switch to EditingMode
 */
open class TaskEditableViewModel(
    val taskRepository: TaskRepository,
    val ogpRepository: OgpRepository,
) : ViewModel() {
    protected var _task =
        MutableLiveData(Task(createdDate = Date(), styleEnum = NodeStyle.HEADLINE_2))
    val task: LiveData<Task> = _task
    var isEditing: Boolean = false

    // ogp
    private val _ogpResult = MutableLiveData<OpenGraphResult?>()
    val ogpResult: LiveData<OpenGraphResult?> = _ogpResult
    private var cachedSiteUrl: String? = null

    private val disposables = CompositeDisposable()

    fun setEditingTask(editingTask: Task) {
        _task.value = editingTask
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

    fun setParentTask(parentTask: Task) {
        _task.value!!.parentTask = parentTask
        _task.value!!.styleEnum =
            if (parentTask.styleEnum.ordinal < NodeStyle.values().size - 1) NodeStyle.values()[parentTask.styleEnum.ordinal + 1]
            else NodeStyle.HEADLINE_2
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
        disposables.add(
            // Not editing mode -> Add new task to DB
            // Editing mode -> update task data in DB
            if (!isEditing) {
                taskRepository.createTask(_task.value!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                    }, {
                        Throwable("Error")
                    })
            } else {
                taskRepository.updateTask(_task.value!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        )
    }

    fun deleteTask(task: Task, onSuccess: () -> Unit = {}) {
        if (isEditing) {
            disposables.add(
                taskRepository.deleteTask(task)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ onSuccess() }, {})
            )
        } else {
            onSuccess()
        }
    }

    private fun notifyChangeToView() {
        _task.value = task.value?.copy() ?: Task()
    }

    // OGP
    private fun fetchOgp(siteUrl: String) {
        cachedSiteUrl = siteUrl // cash site url to reduce extra async task call
        disposables.add(
            ogpRepository.fetchOgp(siteUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    if (it.image != null) { // Only when image url has been detected update data
                        _ogpResult.value = it
                    }
                }
                .doOnError {
                    cachedSiteUrl = null
                    _ogpResult.value = null
                }
                .subscribe({}, {
                    it.printStackTrace()
                })
        )
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

    override fun onCleared() {
        disposables.clear()
    }
}