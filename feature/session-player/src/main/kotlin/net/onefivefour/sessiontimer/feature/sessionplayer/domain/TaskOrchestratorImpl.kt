package net.onefivefour.sessiontimer.feature.sessionplayer.domain

import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup

class TaskOrchestratorImpl(
    private val taskGroups: List<TaskGroup>,
    private val playMode: PlayMode
) : TaskOrchestrator {

    private var currentTaskGroupIndex = 0
    private var currentTaskIndexInGroup = 0

    private lateinit var currentRandomTasks: List<Task>

    override fun getNextTask(): Task? {
        return when (playMode) {
            PlayMode.SEQUENCE -> getSequentialNextTask()
            PlayMode.RANDOM_SINGLE_TASK -> getRandomizedNextTask(1)
            PlayMode.RANDOM_ALL_TASKS -> getRandomizedNextTask(0)
        }
    }

    private fun getSequentialNextTask(): Task? {
        // If no task groups exist, return null
        if (taskGroups.isEmpty()) return null

        // Get current task group
        val currentTaskGroup = taskGroups[currentTaskGroupIndex]

        // If all tasks in current group are completed, move to next group
        if (currentTaskIndexInGroup > currentTaskGroup.tasks.lastIndex) {
            currentTaskGroupIndex++
            currentTaskIndexInGroup = 0

            // Check if all task groups are completed
            if (currentTaskGroupIndex > taskGroups.lastIndex) {
                return null // Session is complete
            }
        }

        // Return next task in sequence
        val nextTask = taskGroups[currentTaskGroupIndex].tasks[currentTaskIndexInGroup]
        currentTaskIndexInGroup++
        return nextTask
    }

    /**
     * @param amount The number of tasks to play per task group.
     * 0 means play all tasks.
     */
    private fun getRandomizedNextTask(amount: Int): Task? {
        // If no task groups exist, return null
        if (taskGroups.isEmpty()) return null

        // Get current task group
        val currentTaskGroup = taskGroups[currentTaskGroupIndex]

        // If we've already processed all tasks in this group, move to next group
        if (currentTaskIndexInGroup >= amount) {
            currentTaskGroupIndex++
            currentTaskIndexInGroup = 0

            // Check if all task groups are completed
            if (currentTaskGroupIndex > taskGroups.lastIndex) {
                return null // Session is complete
            }
        }

        // For the first time in a group, randomly select shuffled tasks
        if (currentTaskIndexInGroup == 0) {
            // Ensure group has at least 2 tasks
            val take = amount.coerceAtMost(currentTaskGroup.tasks.size)

            // Shuffle the tasks and take first 2
            currentRandomTasks = currentTaskGroup.tasks
                .shuffled()
                .take(take)
        }

        // Return next task from randomized selection
        val nextTask = currentRandomTasks[currentTaskIndexInGroup]
        currentTaskIndexInGroup++
        return nextTask
    }
}

