package com.tinyfoxes.translationhelper.util

import com.tinyfoxes.translationhelper.model.TranslationString
import java.io.File
import java.io.FileInputStream
import java.util.prefs.Preferences

object Util {

    fun getPreferences(): Preferences = Preferences.userNodeForPackage(Util::class.java)

    fun printListOfTranslationString(list: List<TranslationString>) {
        list.forEach { item : TranslationString ->
            println("L${item.linenumber} [${item.section}] ${item.key}=${item.translation}")
        }
    }

    fun loadTranslationStrings(text: String): List<TranslationString> {
        val listOfTranslationStrings = mutableListOf<TranslationString>()
        var latestSection: String? = null
        var linenumber = 0
        val lines = text.split("\n")
        lines.forEach { line ->
            linenumber++
            if (line.startsWith(";") || line.isBlank()) {
                //comment line or empty line, skip
                return@forEach
            }
            if (line.startsWith("[")) {
                //section header
                latestSection = line.removePrefix("[").removeSuffix("]")
                return@forEach
            }
            //translation string
            if (line.contains("=")) {
                if (line.last() == '=') {
                    //Key without value
                    listOfTranslationStrings.add(TranslationString(latestSection, line.removeSuffix("="), "", linenumber))
                    return@forEach
                }
                //Key with value
                val (key, value) = line.split("=")
                listOfTranslationStrings.add(TranslationString(latestSection, key, value, linenumber))
            }
        }

        return listOfTranslationStrings
    }

    fun loadTranslationStrings(rootFolder: File, subFolder: String, lang: String): List<TranslationString> {
        val listOfTranslationStrings = mutableListOf<TranslationString>()
        val file = File(rootFolder, "${subFolder}${System.getProperty("file.separator")}${lang}.ini")
        val fis = FileInputStream(file)
        val br = fis.bufferedReader()
        val streamOfString = br.lines()
        var latestSection: String? = null
        var linenumber = 0
        streamOfString.forEach { line ->
            linenumber++
            if (line.startsWith(";") || line.isBlank()) {
                //comment line or empty line, skip
                return@forEach
            }
            if (line.startsWith("[")) {
                //section header
                latestSection = line.removePrefix("[").removeSuffix("]")
                return@forEach
            }
            //translation string
            if (line.contains("=")) {
                if (line.last() == '=') {
                    //Key without value
                    listOfTranslationStrings.add(TranslationString(latestSection, line.removeSuffix("="), "", linenumber))
                    return@forEach
                }
                //Key with value
                val (key, value) = line.split("=")
                listOfTranslationStrings.add(TranslationString(latestSection, key, value, linenumber))
            }
        }
        return listOfTranslationStrings
    }
}