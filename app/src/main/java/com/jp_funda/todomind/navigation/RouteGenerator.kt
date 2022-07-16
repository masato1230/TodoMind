package com.jp_funda.todomind.navigation

import java.util.*

sealed class RouteGenerator(val routeBase: String) {
    open operator fun invoke(): String = routeBase

    class TaskDetail(private val taskId: UUID?) : RouteGenerator(NavigationRoute.TaskDetail) {
        override operator fun invoke() = taskId?.let { "${routeBase}?$it" } ?: run { routeBase }
    }

    class MindMapDetail(private val mindMapId: UUID?) :
        RouteGenerator(NavigationRoute.MindMapDetail) {
        override operator fun invoke(): String {
            return if (mindMapId == null) routeBase else "${routeBase}?${mindMapId}"
        }
    }

    class MindMapCreate(
        private val mindMapId: UUID,
        private val locationX: Float,
        private val locationY: Float,
    ) : RouteGenerator(NavigationRoute.MindMapCreate) {
        override fun invoke() =  "${routeBase}/${mindMapId}/${locationX}/${locationY}"
    }
}