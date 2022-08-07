package com.jp_funda.todomind.sharedpreference

enum class PreferenceKey(val key: String) {
    // settings
    IS_NOT_FIRST_TIME_LAUNCH(key = "is not first time launch"),
    IS_NOT_FIRST_TIME_CREATE_SCREEN(key = "is not first time create screen"),
    DEFAULT_MIND_MAP_SCALE(key = "default mind map scale"),
    IS_SHOW_OGP_THUMBNAIL(key = "is show ogp thumbnail"),
    IS_REMIND_TASK_DEADLINE("is remind task deadline"),
    IS_REVIEW_REQUESTED(key = "is review requested"),
    IS_SHOWED_INTRO(key = "is showed intro"),
    // notification
    REMINDING_TASK_ID(key = "reminding task id"),
}