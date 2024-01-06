package net.onefivefour.sessiontimer.core.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

fun NavGraphBuilder.register(
    navigationApi: NavigationApi,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    navigationApi.registerGraph(
        navGraphBuilder = this,
        navController = navController,
        modifier = modifier
    )
}