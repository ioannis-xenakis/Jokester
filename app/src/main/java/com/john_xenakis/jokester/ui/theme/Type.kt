package com.john_xenakis.jokester.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import john_xenakis.jokester.R

/*
    Jokester is the app for reading jokes and make people laugh.
    Copyright (C) 2022  Ioannis Xenakis

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

    Anything you want to contact me for, contact me with an e-mail, at: xenakis.i.contact@gmail.com
    I'll be happy to help you, or discuss anything with you!
 */

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
        fontSize = 24.sp
    ),

    titleMedium = TextStyle(
        fontFamily = FredokaOne,
        fontSize = 16.sp
    ),

    titleSmall = TextStyle(
        fontFamily = FredokaOne,
        fontSize = 14.sp
    ),

    labelLarge = TextStyle(
        fontFamily = FredokaOne,
        fontSize = 14.sp
    ),

    labelSmall = TextStyle(
        fontFamily = FredokaOne,
        fontSize = 10.sp
    )
)