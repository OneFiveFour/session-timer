package net.onefivefour.sessiontimer.feature.sessionoverview

import net.onefivefour.sessiontimer.core.common.domain.model.Session

data class UiSession(
    val id: Long,
    val title: String
)

fun List<Session>.toUiSessions() = map {
    it.toUiSession()
}

fun Session.toUiSession() = UiSession(
    id = this.id,
    title = this.title
)
