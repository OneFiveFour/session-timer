package net.onefivefour.sessiontimer.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val sairaFontFamily = FontFamily(
    Font(R.font.saira_condensed_regular, FontWeight.Normal)
)

val typography = Typography(
    titleLarge = TextStyle(
        fontFamily = sairaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = sairaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = sairaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)