package net.onefivefour.sessiontimer.feature.sessionplayer.domain

import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal class TaskOrchestratorImpl(
    private val taskGroups: List<TaskGroup>
) : TaskOrchestrator {

    private var currentTaskGroupIndex = 0
    private var durationOfFinishedTasks = Duration.ZERO

    private var currentTaskIndex = 0
    private lateinit var currentTasks: List<Task>

    init {
        initTaskGroup(currentTaskGroupIndex)

    }

    private fun initTaskGroup(taskGroupIndex: Int) {
        if (taskGroupIndex !in taskGroups.indices) {
            return
        }

        val currentTaskGroup = taskGroups[currentTaskGroupIndex]
        currentTasks = if (currentTaskGroup.playMode != PlayMode.SEQUENCE) {
             currentTaskGroup.tasks
                 .shuffled()
                 .take(currentTaskGroup.numberOfRandomTasks)
        } else {
            currentTaskGroup.tasks
        }
    }

    override fun getCurrentTask(): Task? {
        return try {
            taskGroups[currentTaskGroupIndex].tasks[currentTaskIndex]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    override fun getNextTask(): Task? {
        try {
            taskGroups[currentTaskGroupIndex].playMode
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
        return getSequentialNextTask()
    }

    override fun onCurrentTaskFinished() {
        val duration = getCurrentTask()?.duration ?: 0.seconds
        durationOfFinishedTasks += duration
    }

    override fun getDurationOfFinishedTasks(): Duration {
        return durationOfFinishedTasks
    }

    private fun getSequentialNextTask(): Task? {
        // If no task groups exist, return null
        if (taskGroups.isEmpty()) return null

        // Get current task group
        val currentTaskGroup = taskGroups[currentTaskGroupIndex]

        // sequential increase of index
        currentTaskIndex++

        // If all tasks in current group are completed, move to next group
        if (currentTaskIndex > currentTaskGroup.tasks.lastIndex) {
            currentTaskGroupIndex++
            currentTaskIndex = 0

            // Check if all task groups are completed
            if (currentTaskGroupIndex > taskGroups.lastIndex) {
                return null // Session is complete
            }
        }

        // Return next task in sequence
        val nextTask = taskGroups[currentTaskGroupIndex].tasks[currentTaskIndex]
        return nextTask
    }

    /**
     * @param amount The number of tasks to play per task group.
     * No amount or Int.MAX_VALUE means "play all tasks".
     */
    private fun getRandomizedNextTask(amount: Int = Int.MAX_VALUE): Task? {
        // If no task groups exist, return null
        if (taskGroups.isEmpty()) return null

        // Get current task group
        val currentTaskGroup = taskGroups[currentTaskGroupIndex]

        // If we've already processed all tasks in this group, move to next group
        if (currentTaskIndex >= amount) {
            currentTaskGroupIndex++
            currentTaskIndex = 0

            // Check if all task groups are completed
            if (currentTaskGroupIndex > taskGroups.lastIndex) {
                return null // Session is complete
            }
        }

        // For the first time in a group, randomly select shuffled tasks
        if (currentTaskIndex == 0) {
            // Ensure group has at least 2 tasks
            val take = amount.coerceAtMost(currentTaskGroup.tasks.size)

            // Shuffle the tasks and take first 2
            currentTasks = currentTaskGroup.tasks
                .shuffled()
                .take(take)
        }

        // Return next task from randomized selection
        val nextTask = currentTasks[currentTaskIndex]
        currentTaskIndex++
        return nextTask
    }
}

