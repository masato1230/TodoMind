package com.jp_funda.todomind.data.shared_preferences

enum class PreferenceKeys(val key: String) {
    // settings
    DEFAULT_MIND_MAP_SCALE(key = "default mind map scale"),
    IS_SHOW_OGP_THUMBNAIL(key = "is show ogp thumbnail"),
    IS_REMIND_TASK_DEADLINE("is remind task deadline"),
    // notification
    REMINDING_TASK_ID(key = "reminding task id"),
}