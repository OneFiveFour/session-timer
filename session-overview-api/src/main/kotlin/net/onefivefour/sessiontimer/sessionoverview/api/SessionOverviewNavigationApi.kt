package net.onefivefour.sessiontimer.sessionoverview.api

import net.onefivefour.sessiontimer.core.navigation.NavigationApi

interface SessionOverviewNavigationApi : NavigationApi {
    val baseRoute : String
}