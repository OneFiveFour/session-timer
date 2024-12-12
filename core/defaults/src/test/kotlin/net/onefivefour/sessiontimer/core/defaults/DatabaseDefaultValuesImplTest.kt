package net.onefivefour.sessiontimer.core.defaults

import android.content.Context
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

internal class DatabaseDefaultValuesImplTest {

    private val testString = "Test String"

    private val context: Context = mockk<Context>().apply {
        every { getString(any()) } returns testString
    }

    private fun sut() = DatabaseDefaultValuesImpl(
        context
    )

    @Test
    fun `GIVEN sut with context WHEN getSessionTitle is called THEN the correct String is returned`() {
        // GIVEN
        val sut = sut()

        // WHEN
        val result = sut.getSessionTitle()

        // THEN
        assertThat(result).isEqualTo(testString)
    }

    @Test
    fun `GIVEN sut with context WHEN getTaskGroupTitle is called THEN the correct String is returned`() {
        // GIVEN
        val sut = sut()

        // WHEN
        val result = sut.getTaskGroupTitle()

        // THEN
        assertThat(result).isEqualTo(testString)
    }

    @Test
    fun `GIVEN sut with context WHEN getTaskTitle is called THEN the correct String is returned`() {
        // GIVEN
        val sut = sut()

        // WHEN
        val result = sut.getTaskTitle()

        // THEN
        assertThat(result).isEqualTo(testString)
    }
}
