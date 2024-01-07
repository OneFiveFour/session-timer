package net.onefivefour.sessiontimer.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder


interface NavigationApi {

    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    )
}