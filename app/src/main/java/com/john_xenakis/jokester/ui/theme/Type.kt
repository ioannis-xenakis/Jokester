package com.john_xenakis.jokester.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import john_xenakis.jokester.R

/**
 * Fredoka One font, taken from font folder in the app.
 */
private val FredokaOne = FontFamily(
    Font(R.font.fredoka_one)
)

/**
 * Fredoka One typography. This is where Fredoka One font is used for different text styles.
 * For ex. Fredoka One font for different font sizes.
 */
val FredokaOneTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FredokaOne,
        fontSize = 36.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = FredokaOne,
        fontSize = 22.sp
    )
)