package com.jp_funda.todomind.view.mind_map_create.tutorial

enum class TutorialInfo(val assetLink: String, val title: String, val description: String) {
    MOVE_NODE(
        assetLink = "asset:///move_the_node.mp4",
        title = "Move the Node",
        description = "Drag & Drop the node to move it.",
    ),
    EDIT_OR_ADD(
        assetLink = "asset:///edit_or_add.mp4",
        title = "Edit task or Add child",
        description = "Tap a node to edit or add it's child node.",
    ),
    MARK_AS_COMPLETED(
        assetLink = "asset:///mark_as_completed.mp4",
        title = "Mark as Completed",
        description = "Use status drop down to change status of a task(node)."
    ),
    LINK_NODE(
        assetLink = "asset:///link_node.mp4",
        title = "Link Node",
        description = "To create a link node, enter the link in the details field and start a new line.",
    );
}