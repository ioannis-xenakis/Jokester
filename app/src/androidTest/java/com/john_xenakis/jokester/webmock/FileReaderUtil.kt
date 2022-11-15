package com.john_xenakis.jokester.webmock

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * A utility which is used for reading
 * and converting a file(for ex. a Json file),
 * into a String.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
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