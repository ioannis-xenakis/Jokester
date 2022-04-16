package com.john_xenakis.jokester.webmock

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

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
 * A utility which is used for reading
 * and converting a file(for ex. a Json file),
 * into a String.
 *
 * @since 28/9(Sept)/2023
 * @author Ioannis Xenakis
 * @version 1.0.0-beta
 */
object FileReaderUtil {
    /**
     * Converts a file into a String.
     * @param context The current state of the app.
     * @param filePath The file's path of where the file is located in the app structure.
     * @return The converted file into a String.
     */
    fun fileToString(context: Context, filePath: String): String {
        try {
            val inputStream = context.assets.open("network_files/$filePath")
            return inputStreamToString(inputStream)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Converts an Input Stream to a String.
     * @param inputStream The Input Stream of the file.
     * @return The Input Stream converted into a String.
     */
    private fun inputStreamToString(inputStream: InputStream): String {
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}