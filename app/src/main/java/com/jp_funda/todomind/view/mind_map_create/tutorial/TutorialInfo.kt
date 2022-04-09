package com.jp_funda.todomind.view.mind_map_create.tutorial

import com.jp_funda.todomind.R

enum class TutorialInfo(val rawResId: Int, val title: String, val description: String) {
    MOVE_NODE(
        rawResId = R.raw.move_the_node,
        title = "Move the Node",
        description = "Drag & Drop the node to move it.",
    ),
    EDIT_OR_ADD(
        rawResId = R.raw.edit_or_add,
        title = "Edit task or Add child",
        description = "Tap a node to edit or add it's child node.",
    ),
    MARK_AS_COMPLETED(
        rawResId = R.raw.mark_as_completed,
        title = "Mark as Completed",
        description = "Use status drop down to change status of a task(node)."
    ),
    LINK_NODE(
        rawResId = R.raw.link_node,
        title = "Link Node",
        description = "To create a link node, enter the link in the details field and start a new line.",
    );
}