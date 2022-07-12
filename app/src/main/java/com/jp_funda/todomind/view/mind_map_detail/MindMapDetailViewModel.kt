package com.jp_funda.todomind.view.mind_map_detail

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
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.domain.use_cases.mind_map.CreateMindMapUseCase
import com.jp_funda.todomind.domain.use_cases.mind_map.DeleteMindMapUseCase
import com.jp_funda.todomind.domain.use_cases.mind_map.UpdateMindMapUseCase
import com.jp_funda.todomind.util.UrlUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@HiltViewModel
class MindMapDetailViewModel @Inject constructor(
    private val createMindMapUseCase: CreateMindMapUseCase,
    private val updateMindMapUseCase: UpdateMindMapUseCase,
    private val deleteMindMapUseCase: DeleteMindMapUseCase,
    private val ogpRepository: OgpRepository,
    settingsPreferences: SettingsPreferences,
) : ViewModel() {
    private var _mindMap = MutableLiveData(MindMap())
    val mindMap: LiveData<MindMap> = _mindMap

    // ogp
    private val _ogpResult = MutableLiveData<OpenGraphResult?>()
    val ogpResult: LiveData<OpenGraphResult?> = _ogpResult
    private var cachedSiteUrl: String? = null

    var isEditing: Boolean = false
    var isAutoSaveNeeded: Boolean = true

    /** isShowOgpThumbnail */
    val isShowOgpThumbnail = settingsPreferences.getBoolean(PreferenceKeys.IS_SHOW_OGP_THUMBNAIL)


    private val disposables = CompositeDisposable()

    fun setEditingMindMap(editingMindMap: MindMap) {
        _mindMap.value = editingMindMap
        isEditing = true
    }

    fun setTitle(title: String) {
        _mindMap.value!!.title = title
        notifyChangeToView()
    }

    fun setDescription(description: String) {
        _mindMap.value!!.description = description
        notifyChangeToView()
    }

    fun setX(x: Float) {
        _mindMap.value!!.x = x
    }

    fun setColor(argb: Int) {
        _mindMap.value!!.color = argb
        notifyChangeToView()
    }

    fun setIsCompleted(isCompleted: Boolean) {
        _mindMap.value!!.isCompleted = isCompleted
        notifyChangeToView()
    }

    fun saveMindMap() {
        CoroutineScope(Dispatchers.IO).launch {
            if (!isEditing) {
                createMindMapUseCase(_mindMap.value!!)
            } else {
                updateMindMapUseCase(_mindMap.value!!)
            }
        }
    }

    fun deleteMindMap() {
        isAutoSaveNeeded = false
        if (isEditing) {
            viewModelScope.launch(Dispatchers.IO) {
                deleteMindMapUseCase(_mindMap.value!!)
            }
        }
    }

    private fun notifyChangeToView() {
        _mindMap.value = mindMap.value?.copy() ?: MindMap()
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
}