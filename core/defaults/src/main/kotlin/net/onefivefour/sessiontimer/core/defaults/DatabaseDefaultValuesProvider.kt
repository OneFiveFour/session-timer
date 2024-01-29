package net.onefivefour.sessiontimer.core.defaults

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import net.onefivefour.sessiontimer.core.database.data.DefaultValuesProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseDefaultValuesProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : DefaultValuesProvider {

    override fun getSessionTitle() = context.getString(R.string.default_session_title)
    override fun getTaskGroupTitle() = context.getString(R.string.default_taskgroup_title)
    override fun getTaskTitle() = context.getString(R.string.default_task_title)

}