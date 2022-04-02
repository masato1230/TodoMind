package com.jp_funda.todomind.view.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.IntroPage

enum class IntroPageInfo(
    val mainText: String,
    val subText: String,
    val copyrightText: String? = null,
    val thumbnail: @Composable () -> Unit,
) {
    AboutMindTodo(
        mainText = "TodoMind",
        subText = "TodoMind is a task management application based on the idea of Goals Breakdown Structure(GBS).",
        thumbnail = {
            Box(
                modifier = androidx.compose.ui.Modifier
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
                    .background(colorResource(id = com.jp_funda.todomind.R.color.light_purple))
            ) {
                Image(
                    painter = painterResource(id = com.jp_funda.todomind.R.drawable.ic_mind_map),
                    contentDescription = "App icon",
                    modifier = androidx.compose.ui.Modifier
                        .padding(20.dp)
                        .size(100.dp)
                )
            }
        }
    ),
    SetYourGoal(
        mainText = "1. Set your goal",
        subText = "create new mind map and set your goal as the title.",
        copyrightText = "(undraw.co)",
        thumbnail = {
            Image(
                painter = painterResource(id = com.jp_funda.todomind.R.drawable.img_set_your_goal),
                contentDescription = "Goal",
                modifier = androidx.compose.ui.Modifier
                    .fillMaxSize()
            )
        },
    ),
    BreakDownGoal(
        mainText = "2. Breakdown your goal",
        subText = "Breakdown your goal to more detailed goals or tasks.",
        thumbnail = {
            Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.img_mind_map_sample),
                contentDescription = "mind map sample",
                modifier = Modifier
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(5.dp))
            )
        }
    ),
    ManageTasksByList(
        mainText = "3. Manage tasks by To Do List.",
        subText = "Manage tasks organized in mind map by TO DO list and set reminders.",
        thumbnail = {
            Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.img_task_list_sample),
                contentDescription = "mind map sample",
                modifier = Modifier
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(5.dp))
            )
        }
    ),
    Achieve(
        mainText = "4. Achieve your Goal!",
        subText = "Do tasks one by one and achieve your goal.",
        copyrightText = "(undraw.co)",
        thumbnail = {
            Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.img_archieve),
                contentDescription = "Achieve",
                modifier = Modifier
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(5.dp))
            )
        }
    );

    val compose: @Composable () -> Unit
        get() {
            return {
                IntroPage(
                    thumbnail = { thumbnail() },
                    mainText = mainText,
                    subText = subText,
                    copyrightText = copyrightText,
                )
            }
        }
}