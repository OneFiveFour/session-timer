package net.onefivefour.sessiontimer.core.common.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class SessionExtTest {

    @Test
    fun `GIVEN a normal session WHEN getTotalDuration is called THEN correct duration is returned`() {
        // GIVEN
        val session = FAKE_SESSION

        // WHEN
        val result = session.getTotalDuration()

        // THEN
        assertThat(result).isEqualTo(20.seconds)
    }

    @Test
    fun `GIVEN a session wihtout tasks WHEN getTotalDuration is called THEN Duration Zero is returned`() {
        // GIVEN
        val session = FAKE_SESSION.copy(taskGroups = emptyList())

        // WHEN
        val result = session.getTotalDuration()

        // THEN
        assertThat(result).isEqualTo(Duration.ZERO)
    }

}