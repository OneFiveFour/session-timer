package net.onefivefour.sessiontimer.core.defaults

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode.SEQUENCE
import net.onefivefour.sessiontimer.core.database.data.DatabaseDefaultValues
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseDefaultValuesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DatabaseDefaultValues {

    override fun getSessionTitle() = context.getString(R.string.default_session_title)
    override fun getTaskGroupTitle() = context.getString(R.string.default_taskgroup_title)
    override fun getTaskGroupColor() = 0xFF000000
    override fun getTaskGroupPlayMode() = SEQUENCE
    override fun getTaskGroupNumberOfRandomTasks() = 1
    override fun getTaskTitle() = context.getString(R.string.default_task_title)
    override fun getTaskDuration() = 3

}