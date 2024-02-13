package net.onefivefour.sessiontimer.core.database.data

interface DatabaseDefaultValues {
    fun getSessionTitle(): String
    fun getTaskGroupTitle(): String
    fun getTaskGroupColor(): Int
    fun getTaskTitle(): String
    fun getTaskDuration(): Int
}
