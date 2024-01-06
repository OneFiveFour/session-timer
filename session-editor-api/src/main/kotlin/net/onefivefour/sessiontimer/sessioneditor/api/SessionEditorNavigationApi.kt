package net.onefivefour.sessiontimer.sessioneditor.api

import net.onefivefour.sessiontimer.core.navigation.NavigationApi

interface SessionEditorNavigationApi : NavigationApi {
    val sessionEditorRoute : String
}