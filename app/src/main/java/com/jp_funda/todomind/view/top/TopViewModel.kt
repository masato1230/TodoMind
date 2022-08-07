package com.jp_funda.todomind.view.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp_funda.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.sharedpreference.PreferenceKey
import com.jp_funda.todomind.sharedpreference.SettingsPreference
import com.jp_funda.todomind.use_case.mind_map.GetMostRecentlyUpdatedMindMapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val getMostRecentlyUpdatedMindMapUseCase: GetMostRecentlyUpdatedMindMapUseCase,
    private val settingsPreferences: SettingsPreference,
) : ViewModel() {
    private var _mostRecentlyUpdatedMindMap = MutableLiveData<MindMap?>(null)
    val mostRecentlyUpdatedMindMap: LiveData<MindMap?> = _mostRecentlyUpdatedMindMap

    val isReviewRequested =
        settingsPreferences.getBoolean(PreferenceKey.IS_REVIEW_REQUESTED)

    fun getMostRecentlyUpdatedMindMap() {
        viewModelScope.launch(Dispatchers.IO) {
            val mostRecentlyUpdatedMindMap = getMostRecentlyUpdatedMindMapUseCase()
            _mostRecentlyUpdatedMindMap.postValue(mostRecentlyUpdatedMindMap)
        }
    }

    fun setIsReviewRequested(isRequested: Boolean) {
        settingsPreferences.setBoolean(PreferenceKey.IS_REVIEW_REQUESTED, isRequested)
    }
}