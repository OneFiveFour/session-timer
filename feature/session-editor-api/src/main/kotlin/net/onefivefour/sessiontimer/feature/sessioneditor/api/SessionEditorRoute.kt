package net.onefivefour.sessiontimer.feature.sessioneditor.api

import kotlinx.serialization.Serializable

@Serializable
data class SessionEditorRoute(
    val sessionId: Long
)