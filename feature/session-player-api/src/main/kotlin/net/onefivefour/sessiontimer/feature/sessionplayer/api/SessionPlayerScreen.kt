package net.onefivefour.sessiontimer.feature.sessionplayer.api

import androidx.compose.runtime.Composable

interface SessionPlayerScreen {

    @Composable
    operator fun invoke() : @Composable () -> Unit
}