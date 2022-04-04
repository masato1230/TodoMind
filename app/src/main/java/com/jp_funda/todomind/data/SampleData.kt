package com.jp_funda.todomind.data

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus

object SampleData {
    val mindMap = MindMap(
        title = "Become a better Android Developer",
        description = "This is a sample mind map. Please delete.",
        isCompleted = false,
        x = 5469.2354f,
        y = 1364.0999f,
        color = -1499549,
    )
    val programmingLanguage = Task(
        mindMap = mindMap,
        title = "Programming Language",
        description = "This is a sample task. Please delete sample mind map.",
        x = 2071.1033f,
        y = 4273.861f,
        color = -11751600,
        styleEnum = NodeStyle.HEADLINE_2,
        statusEnum = TaskStatus.Open,
    )
    val versionControl = Task(
        mindMap = mindMap,
        title = "Version Control",
        description = "This is a sample task. Please delete sample mind map.",
        x = 4405.6094f,
        y = 3951.02f,
        color = -13154481,
        styleEnum = NodeStyle.HEADLINE_2,
        statusEnum = TaskStatus.Open,
    )
    val masterMajorLibraries = Task(
        mindMap = mindMap,
        title = "Master Major Libraries",
        description = "This is a sample task. Please delete sample mind map.",
        x = 6898.452f,
        y = 6417.525f,
        color = -12627531,
        styleEnum = NodeStyle.HEADLINE_2,
        statusEnum = TaskStatus.Open,
    )
    val learnEnglish = Task(
        mindMap = mindMap,
        title = "Learn English",
        description = "This is a sample task. Please delete sample mind map.",
        x = 8059.715f,
        y = 3948.9238f,
        color = -1023342,
        styleEnum = NodeStyle.HEADLINE_2,
        statusEnum = TaskStatus.Open,
    )
    val kotlin = Task(
        mindMap = mindMap,
        title = "kotlin",
        description = "This is a sample task. Please delete sample mind map.",
        x = 985.1459f,
        y = 592.839f,
        parentTask = programmingLanguage,
        color = -13730510,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Open,
    )
    val gitCommands = Task(
        mindMap = mindMap,
        title = "git commands",
        description = "This is a sample task. Please delete sample mind map.",
        x = 4740.6094f,
        y = 5101.8584f,
        parentTask = versionControl,
        color = -7297874,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Complete,
    )
    val github = Task(
        mindMap = mindMap,
        title = "github",
        description = "This is a sample task. Please delete sample mind map.",
        x = 3826.6216f,
        y = 6004.958f,
        parentTask = versionControl,
        color = -14273992,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Open,
    )
    val rxJava = Task(
        mindMap = mindMap,
        title = "RxJava",
        description = "This is a sample task. Please delete sample mind map.",
        x = 6253.9365f,
        y = 6035.3716f,
        parentTask = masterMajorLibraries,
        color = -14142061,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Open,
    )
    val jetpackCompose = Task(
        mindMap = mindMap,
        title = "jetpack compose",
        description = "This is a sample task. Please delete sample mind map.",
        x = 6068.452f,
        y = 6630.861f,
        parentTask = masterMajorLibraries,
        color = -14983648,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Open,
    )
    val reading = Task(
        mindMap = mindMap,
        title = "Reading",
        description = "This is a sample task. Please delete sample mind map.",
        x = 8899.12f,
        y = 4158.33f,
        parentTask = learnEnglish,
        color = -5434281,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Complete,
    )
    val writing = Task(
        mindMap = mindMap,
        title = "Writing",
        description = "This is a sample task. Please delete sample mind map.",
        x = 8479.728f,
        y = 4704.9956f,
        parentTask = learnEnglish,
        color = -1023342,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Open,
    )
    val learnByWriteStackoverflowAnswers = Task(
        mindMap = mindMap,
        title = "learn by write stackoberflow answers",
        description = "This is a sample task. Please delete sample mind map.",
        x = 9173.0625f,
        y = 5146.659f,
        parentTask = writing,
        color = -5434281,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Open,
    )
    val write50Answers = Task(
        mindMap = mindMap,
        title = "Write 50 answers",
        description = "This is a sample task. Please delete sample mind map.",
        x = 9774.7295f,
        y = 5484.9976f,
        parentTask = learnByWriteStackoverflowAnswers,
        color = -476208,
        styleEnum = NodeStyle.BODY_1,
        statusEnum = TaskStatus.InProgress,
    )
    val takeAnUdemyCourse = Task(
        mindMap = mindMap,
        title = "Take an Udemy Course",
        description = "This is a sample task. Please delete sample mind map.",
        x = 4855.0757f,
        y = 5835.87f,
        parentTask = gitCommands,
        color = -10720320,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Complete,
    )
    val room = Task(
        mindMap = mindMap,
        title = "Room",
        description = "This is a sample task. Please delete sample mind map.",
        x = 6228.766f,
        y = 7149.086f,
        parentTask = masterMajorLibraries,
        color = -10044566,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Open,
    )
    val realm = Task(
        mindMap = mindMap,
        title = "Realm",
        description = "This is a sample task. Please delete sample mind map.",
        x = 6759.848f,
        y = 7391.556f,
        parentTask = masterMajorLibraries,
        color = -13730510,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Open,
    )
    val hilt = Task(
        mindMap = mindMap,
        title = "Hilt",
        description = "This is a sample task. Please delete sample mind map.",
        x = 7318.4556f,
        y = 7430.859f,
        parentTask = masterMajorLibraries,
        color = -16738680,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Complete,
    )
    val retrofit = Task(
        mindMap = mindMap,
        title = "Retrofit",
        description = "This is a sample task. Please delete sample mind map.",
        x = 7838.4575f,
        y = 7112.5264f,
        parentTask = masterMajorLibraries,
        color = -14575885,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Open,
    )
    val architecture = Task(
        mindMap = mindMap,
        title = "Architecture",
        description = "This is a sample task. Please delete sample mind map.",
        x = 2677.106f,
        y = 5470.5205f,
        parentTask = programmingLanguage,
        color = -769226,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Open,
    )
    val mvvmPlusRepository = Task(
        mindMap = mindMap,
        title = "MVVM+Repository",
        description = "This is a sample task. Please delete sample mind map.",
        x = 2307.0706f,
        y = 6302.205f,
        parentTask = architecture,
        color = -3790808,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Open,
    )
    val androidSpecificTopics = Task(
        mindMap = mindMap,
        title = "Android Specific Topics",
        description = "This is a sample task. Please delete sample mind map.",
        x = 8769.159f,
        y = 6195.292f,
        parentTask = null,
        color = -5317,
        styleEnum = NodeStyle.HEADLINE_2,
        statusEnum = TaskStatus.Open,
    )
    val proguardRule = Task(
        mindMap = mindMap,
        title = "proguard rule",
        description = "This is a sample task. Please delete sample mind map.",
        x = 9577.38f,
        y = 6308.443f,
        parentTask = androidSpecificTopics,
        color = -278483,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Complete,
    )
    val cleanArchitecture = Task(
        mindMap = mindMap,
        title = "Clean Architecture",
        description = "This is a sample task. Please delete sample mind map.",
        x = 3039.1057f,
        y = 6314.5205f,
        parentTask = architecture,
        color = -1092784,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Open,
    )
    val dataStructureAndAlgorithm = Task(
        mindMap = mindMap,
        title = "Data Structure & Algorithm",
        description = "This is a sample task. Please delete sample mind map.",
        x = 1358.203f,
        y = 6841.7446f,
        parentTask = kotlin,
        color = -10044566,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Open,
    )
    val oopBasics = Task(
        mindMap = mindMap,
        title = "OOP basics",
        description = "This is a sample task. Please delete sample mind map.",
        x = 366.8124f,
        y = 6654.8364f,
        parentTask = kotlin,
        color = -5319295,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Open,
    )
    val masterInOjt = Task(
        mindMap = mindMap,
        title = "Master in OJT",
        description = "This is a sample task. Please delete sample mind map.",
        x = 4135.917f,
        y = 7034.5645f,
        parentTask = github,
        color = -10453621,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Open,
    )
    val makeOssContribution = Task(
        mindMap = mindMap,
        title = "Make OSS contribution",
        description = "This is a sample task. Please delete sample mind map.",
        x = 3509.0024f,
        y = 6980.296f,
        parentTask = github,
        color = -13154481,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Open,
    )
    val create200IssuesOrPullRequest = Task(
        mindMap = mindMap,
        title = "Create 200 issues or pull request",
        description = "This is a sample task. Please delete sample mind map.",
        x = 4662.158f,
        y = 7788.2341f,
        parentTask = masterInOjt,
        color = -14273992,
        styleEnum = NodeStyle.BODY_1,
        statusEnum = TaskStatus.Complete,
    )
    val findProjectToContribute = Task(
        mindMap = mindMap,
        title = "Find Project to contribute",
        description = "This is a sample task. Please delete sample mind map.",
        x = 4133.8613f,
        y = 8072.2144f,
        parentTask = makeOssContribution,
        color = -657931,
        styleEnum = NodeStyle.BODY_1,
        statusEnum = TaskStatus.InProgress,
    )
    val marge10PullRequests = Task(
        mindMap = mindMap,
        title = "Marge 10 pull requests",
        description = "This is a sample task. Please delete sample mind map.",
        x = 4211.3994f,
        y = 8383.095f,
        parentTask = makeOssContribution,
        color = -4342339,
        styleEnum = NodeStyle.BODY_1,
        statusEnum = TaskStatus.Open,
    )
    val githubActions = Task(
        mindMap = mindMap,
        title = "github actions",
        description = "This is a sample task. Please delete sample mind map.",
        x = 4644.957f,
        y = 6870.791f,
        parentTask = github,
        color = -11243910,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Open,
    )
    val kotlinMultiPlatform = Task(
        mindMap = mindMap,
        title = "Kotlin multi platform",
        description = "This is a sample task. Please delete sample mind map.",
        x = 793.2669f,
        y = 6866.5527f,
        parentTask = kotlin,
        color = -14142061,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Open,
    )
    val tdd = Task(
        mindMap = mindMap,
        title = "TDD",
        description = "This is a sample task. Please delete sample mind map.",
        x = 9164.167f,
        y = 6991.9614f,
        parentTask = androidSpecificTopics,
        color = -16121,
        styleEnum = NodeStyle.HEADLINE_3,
        statusEnum = TaskStatus.Open,
    )
    val gitCourse = Task(
        mindMap = mindMap,
        title = "Git course",
        description = "I'm really enjoying this course on Udemy and think you might like it too.\n" +
                "https://www.udemy.com/share/101XzE3@Y31GzSZUpDFnW7S1RDgVZmUoUrcWcGBoPV7M0zs5n6qpjHGRCW4twO2sP659IIa36A==/\n",
        x = 5353.8247f,
        y = 6180.8696f,
        parentTask = takeAnUdemyCourse,
        color = -8875876,
        styleEnum = NodeStyle.LINK,
        statusEnum = TaskStatus.Complete,
    )
    val joinToCommunity = Task(
        mindMap = mindMap,
        title = "Join to community",
        description = "This is a sample task. Please delete sample mind map.",
        x = 1875.1461f,
        y = 6726.3374f,
        parentTask = kotlin,
        color = -10044566,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Open,
    )
    val tryAtOwnProjectForAnWeek = Task(
        mindMap = mindMap,
        title = "Try at own project for an week",
        description = "This is a sample task. Please delete sample mind map.",
        x = 9668.439f,
        y = 7619.14f,
        parentTask = tdd,
        color = -415707,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.InProgress,
    )
    val tryQuickStartAsyncSectionInDocumentation = Task(
        mindMap = mindMap,
        title = "Try quick start async section in documentation",
        description = "This is a sample task. Please delete sample mind map.",
        x = 7066.2583f,
        y = 8216.089f,
        parentTask = realm,
        color = -14983648,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Complete,
    )
    val learnByGoogleCodeLab = Task(
        mindMap = mindMap,
        title = "Learn by Google code lab",
        description = "This is a sample task. Please delete sample mind map.",
        x = 7825.1226f,
        y = 8032.527f,
        parentTask = hilt,
        color = -14244198,
        styleEnum = NodeStyle.HEADLINE_4,
        statusEnum = TaskStatus.Complete,
    )
    val tryHandsOnBookByPackt = Task(
        mindMap = mindMap,
        title = "Try hands on book by packt",
        description = "This is a sample task. Please delete sample mind map.",
        x = 1633.2032f,
        y = 7434.2466f,
        parentTask = dataStructureAndAlgorithm,
        color = -13730510,
        styleEnum = NodeStyle.BODY_1,
        statusEnum = TaskStatus.InProgress,
    )
    val handsOnDataStructure = Task(
        mindMap = mindMap,
        title = "hands on data structure",
        description = "This is a sample task. Please delete sample mind map.",
        x = 1770.7932f,
        y = 7626.931f,
        parentTask = tryHandsOnBookByPackt,
        color = -26624,
        styleEnum = NodeStyle.LINK,
        statusEnum = TaskStatus.Open,
    )
}