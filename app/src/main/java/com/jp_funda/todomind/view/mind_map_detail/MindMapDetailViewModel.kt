package com.jp_funda.todomind.view.mind_map_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.ogp.entity.OpenGraphResult
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.util.UrlUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MindMapDetailViewModel @Inject constructor(
    private val mindMapRepository: MindMapRepository,
    private val ogpRepository: OgpRepository,
    settingsPreferences: SettingsPreferences,
    ) : ViewModel() {
    private var _mindMap = MutableLiveData(MindMap(createdDate = Date()))
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

    fun saveMindMapAndClearDisposables() {
        disposables.add(
            if (!isEditing) {
                mindMapRepository.createMindMap(_mindMap.value!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { disposables.clear() }
                    .subscribe()
            } else {
                mindMapRepository.updateMindMap(_mindMap.value!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { disposables.clear() }
                    .subscribe()
            }
        )
    }

    fun deleteMindMapAndClearDisposables(onSuccess: () -> Unit = {}) {
        isAutoSaveNeeded = false
        if (isEditing) {
            disposables.add(
                mindMapRepository.deleteMindMap(_mindMap.value!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess {
                        delay { // delay to wait dismissing dialog
                            onSuccess()
                            disposables.clear()
                        }
                    }
                    .subscribe()
            )
        } else {
            delay {
                onSuccess()
            }
        }
    }

    private fun delay(onFinally: () -> Unit = {}) {
        disposables.add(
            Completable
                .timer(50, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()) // where the work should be done
                .observeOn(AndroidSchedulers.mainThread()) // where the data stream should be delivered
                .subscribe({
                    // do something after 1 second
                    onFinally()
                    disposables.clear()
                }, {
                    // do something on error
                })
        )
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