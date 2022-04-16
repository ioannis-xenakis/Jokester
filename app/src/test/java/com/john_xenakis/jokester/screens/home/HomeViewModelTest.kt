package com.john_xenakis.jokester.screens.home

import com.john_xenakis.jokester.data.models.JokeFlagsContent
import org.junit.Assert.assertEquals
import org.junit.Test
import timber.log.Timber

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
 * Unit test, for testing the ViewModel for home screen.
 *
 * @since 28/9(Sept)/2023
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
class HomeViewModelTest {

    /**
     * Unit test for testing
     * if convertCheckedFlagsToStringWithCommas works correctly
     * and returns the correct output.
     */
    @Test
    fun testConvertCheckedFlagsStringCommasIsCorrect() {
        val string = convertCheckedFlagsToStringWithCommas(
            listOf(
                JokeFlagsContent(1, false, "nsfw"),
                JokeFlagsContent(2, true, "religious"),
                JokeFlagsContent(3, true, "racist")
            )
        )
        Timber.d("test convertCheckedFlags: $string")
        assertEquals("nsfw,religious,racist", string)
    }

    /**
     * Converts the checked flags list into a single string separated with commas.
     * @param checkedFlagsList The checked flags list(list of JokeFlagsContent class)
     * @return The checked flags into a single string
     */
    private fun convertCheckedFlagsToStringWithCommas(checkedFlagsList: List<JokeFlagsContent>): String? {
        var checkedFlagsString: String?
        if (checkedFlagsList.isNotEmpty()) {
            checkedFlagsString = ""
            checkedFlagsList.forEach { jokeFlagsContent ->
                checkedFlagsString += ",${jokeFlagsContent.jokeFlagName}"
            }
            checkedFlagsString = checkedFlagsString.removePrefix(",")
        } else {
            checkedFlagsString = null
        }

        return checkedFlagsString
    }

}