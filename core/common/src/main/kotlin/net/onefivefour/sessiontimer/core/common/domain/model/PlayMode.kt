package net.onefivefour.sessiontimer.core.common.domain.model

enum class PlayMode {
    /**
     * Play all tasks in all task groups in sequence.
     */
    SEQUENCE,

    /**
     * Play a randomly chosen single task from each task group.
     */
    RANDOM_SINGLE_TASK,

    /**
     * Play a randomly chosen subset of n tasks from each task group.
     */
    RANDOM_N_TASKS,

    /**
     * Play all tasks in all task groups shuffled.
     */
    RANDOM_ALL_TASKS
}
