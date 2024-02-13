package net.onefivefour.sessiontimer.feature.sessionoverview.api

import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Home
import de.onecode.navigator.api.Navigation
import net.onefivefour.sessiontimer.feature.sessioneditor.api.SessionEditor
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayer

@Destination
@Home
@Navigation(to = SessionEditor::class)
@Navigation(to = SessionPlayer::class)
object SessionOverview
