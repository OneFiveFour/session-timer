package net.onefivefour.sessiontimer.core.test

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.internalToRoute
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class StandardTestDispatcherRule : TestWatcher() {

    val testDispatcher = StandardTestDispatcher()

    @SuppressLint("RestrictedApi")
    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher)
        super.starting(description)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
        super.finished(description)
    }
}