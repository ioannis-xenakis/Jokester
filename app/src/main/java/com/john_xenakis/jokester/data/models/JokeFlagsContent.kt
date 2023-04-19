package com.john_xenakis.jokester.data.models

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
 * The joke flags content data class
 * regarding on what each joke flag should contain, on user interface.
 *
 * @since 28/9(Sept)/2023
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
data class JokeFlagsContent(

    /**
     * The joke flag number id, identifying each joke flag.
     */
    var jokeFlagId: Int = 1,

    /**
     * The boolean for identifying if the joke list is being filtered or not by the flag.
     */
    var filteredBoolean: Boolean = false,

    /**
     * The name of the joke flag in a string.
     */
    val jokeFlagName: String
)