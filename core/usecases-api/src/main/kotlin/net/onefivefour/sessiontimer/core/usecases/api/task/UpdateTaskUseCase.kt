package net.onefivefour.sessiontimer.core.usecases.api.task

import kotlin.time.Duration

interface UpdateTaskUseCase {
    suspend fun execute(
        taskId: Long,
        title: String,
        duration: Duration
    )
}