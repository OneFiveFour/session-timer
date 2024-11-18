package net.onefivefour.sessiontimer.core.defaults

import android.content.Context
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class DatabaseDefaultValuesImplTest {

    private val testString = "Test String"

    private val context: Context = mockk<Context>().apply {
        every { getString(any()) } returns testString
    }

    private val sut = DatabaseDefaultValuesImpl(
        context
    )

    @Test
    fun `getSessionTitle returns correct String`() {
        val result = sut.getSessionTitle()
        assertThat(result).isEqualTo(testString)
    }

    @Test
    fun `getTaskGroupTitle returns correct String`() {
        val result = sut.getTaskGroupTitle()
        assertThat(result).isEqualTo(testString)
    }

    @Test
    fun `getTaskTitle returns correct String`() {
        val result = sut.getTaskTitle()
        assertThat(result).isEqualTo(testString)
    }
}
