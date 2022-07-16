package com.jp_funda.todomind.navigation

import java.util.*

sealed class RouteGenerator(val routeBase: String) {
    open operator fun invoke(): String = routeBase

    class TaskDetail(private val taskId: UUID) : RouteGenerator(NavigationRoute.TaskDetail) {
        override operator fun invoke(): String {
            return "${routeBase}?${taskId}" // TODO use path parameter
        }
    }

    class MindMapDetail(private val mindMapId: UUID) :
        RouteGenerator(NavigationRoute.MindMapDetail) {
        override operator fun invoke(): String {
            return "${routeBase}?${mindMapId}" // TODO use path parameter
        }
    }

    class MindMapCreate(
        private val mindMapId: UUID,
        private val locationX: Float,
        private val locationY: Float,
    ) : RouteGenerator(NavigationRoute.MindMapCreate) {
        override fun invoke(): String {
            return "${routeBase}/${mindMapId}/${locationX}/${locationY}"
        }
    }
}