package net.onefivefour.sessiontimer.core.common.domain.model

import kotlin.time.Duration.Companion.seconds

/**
 * Contains 1 TaskGroup with 2 Tasks.
 * The TaskGroup is set to PlayMode.SEQUENCE.
 */
val FAKE_SESSION = Session(
    id = 1L,
    title = "Test Session",
    taskGroups = listOf(
        TaskGroup(
            id = 2L,
            title = "Test Task Group",
            color = 0xFF0000,
            playMode = PlayMode.SEQUENCE,
            tasks = listOf(
                Task(
                    id = 3L,
                    title = "Test Task 3L",
                    duration = 10.seconds,
                    taskGroupId = 2L
                ),
                Task(
                    id = 4L,
                    title = "Test Task 4L",
                    duration = 10.seconds,
                    taskGroupId = 2L
                )
            ),
            sessionId = 1L
        )
    )
)