package com.jp_funda.todomind.view.mind_map_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MindMapCreateViewModel @Inject constructor(
    private val mindMapRepository: MindMapRepository,
    private val taskRepository: TaskRepository,
) : ViewModel() {
    /** isLoading - flag for initial DB loading is finished */
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    /** Scale factor */
    private val _scale = MutableLiveData(1f)
    val scale: LiveData<Float> = _scale

    /** MindMap - initialize at Fragment's onCreate */
    lateinit var mindMap: MindMap

    /** Tasks - all tasks in mind map. initialize at Fragment's onCreate */
    private lateinit var tasks: List<Task>

    private val disposables = CompositeDisposable()

    fun setScale(scale: Float) {
        _scale.value = scale
    }

    /** Load all data from db which is needed for drawing selected mind map */
    fun loadTaskData() {
        disposables.add(
            taskRepository
                .getTasksInAMindMap(mindMap)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    tasks = it
                    _isLoading.value = false
                }
                .subscribe({}, {
                    it.printStackTrace()
                })
        )
    }

    fun updateMindMap(mindMap: MindMap) {
        this.mindMap = mindMap
        disposables.add(
            mindMapRepository.updateMindMap(mindMap)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}