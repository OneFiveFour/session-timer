package net.onefivefour.sessiontimer.feature.sessionplayer.api

import kotlinx.serialization.Serializable

@Serializable
data class SessionPlayerRoute(
    val sessionId: Long
)
