package net.onefivefour.sessiontimer.core.database.data

interface DefaultValuesProvider {
    fun getSessionTitle() : String
    fun getTaskGroupTitle() : String
    fun getTaskTitle() : String
}