package net.onefivefour.sessiontimer.core.database.test

import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.domain.DatabaseDefaultValues

object DatabaseDefaultValuesFake : DatabaseDefaultValues {

    override fun getSessionTitle(): String {
        return "DEFAULT_SESSION_TITLE"
    }

    override fun getTaskGroupTitle(): String {
        return "DEFAULT_SESSION_TITLE"
    }

    override fun getTaskGroupColor(): Long {
        return 0xFF0000L
    }

    override fun getTaskGroupPlayMode(): PlayMode {
        return PlayMode.SEQUENCE
    }

    override fun getTaskGroupNumberOfRandomTasks(): Int {
        return 15
    }

    override fun getTaskTitle(): String {
        return "DEFAULT_TASK_TITLE"
    }

    override fun getTaskDuration(): Int {
        return 9
    }
}
