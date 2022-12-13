import java.io.File
import java.io.FileInputStream
import kotlin.system.exitProcess

var rootFolder: File? = null //File("/Users/frankbouwens/priv/Outfox/Tiny-Foxes/OutFox-NL/")
var sourceLangCode: String? = null //"en"
var targetLangCode: String? = null //"nl"

fun main(args: Array<String>) {
    println("TranslationHelper for Project OutFox; by FrankkieNL")

    while (true) {
        startMenu()
    }
}

fun startMenu() {
    println(
        """
        Menu:
        0) Exit
        1) Check for missing keys
    """.trimIndent()
    )
    val input = readln()
    if (input.toIntOrNull() != null) {
        when (input.toInt()) {
            0 -> exitProcess(0)
            1 -> checkForMissingKeys()
            else -> println("Number not found")
        }
    }
}

fun printListOfTranslationString(list: List<TranslationString>) {
    list.forEach { item ->
        println("L${item.linenumber} [${item.section}] ${item.key}=${item.translation}")
    }
}

fun askRootFolder() {
    println("Please provide the root folder for the translations: (full path)\n (e.g. /Users/frankbouwens/priv/Outfox/Tiny-Foxes/OutFox-NL/ )")
    val input = readln()
    val tempFile = File(input)
    if (!tempFile.exists()) {
        println("File does not exist")
        return
    }
    if (!tempFile.canRead()) {
        println("File cannot be read")
        return
    }
    if (!tempFile.isDirectory) {
        println("File is not a directory")
        return
    }
    //All ok
    rootFolder = tempFile
    rootFolder?.let { safeRootFolder ->
        println("Set as root folder: ${safeRootFolder.absolutePath}")
    }
}

fun askLangCode(prompt: String): String? {
    println(prompt)
    val langCode = readln().lowercase()
    if (langCode.length != 2 || !"\\w\\w".toRegex().matches(langCode)) {
        println("That is not a 2 letter language code \"$langCode\".")
        return null
    }
    return langCode
}

fun checkForMissingKeys() {
    if (rootFolder == null) {
        askRootFolder()
        return
    }

    if (sourceLangCode == null) {
        sourceLangCode = askLangCode("Please enter source language (as 2-letter code, e.g. 'en')") ?: return
    }
    val safeSourceLangCode = sourceLangCode?:return

    if (targetLangCode == null) {
        targetLangCode = askLangCode("Please enter target language (as 2-letter code, e.g. 'nl')") ?: return
    }
    val safeTargetLangCode = targetLangCode?:return

    val sourceStrings = loadTranslationStrings("_fallback", safeSourceLangCode)
    val targetStrings = loadTranslationStrings("_fallback", safeTargetLangCode)

    var numMissingSourceLang = 0
    var numMissingTargetLang = 0
    println("Checking for missing keys...")
    println("$safeSourceLangCode strings missing in the $safeTargetLangCode strings: ")
    sourceStrings.forEach { sourceString ->
        if (!targetStrings.contains(sourceString)) {
            println("L${sourceString.linenumber} [${sourceString.section}] ${sourceString.key}=${sourceString.translation}")
            numMissingSourceLang++
        }
    }
    if (numMissingSourceLang==0) {
        println("No missing strings")
    }

    println("$safeTargetLangCode string missing in the $safeSourceLangCode strings: ")
    targetStrings.forEach { targetString ->
        if (!sourceStrings.contains(targetString)) {
            println("L${targetString.linenumber} [${targetString.section}] ${targetString.key}=${targetString.translation}")
            numMissingTargetLang++
        }
    }
    if (numMissingTargetLang==0) {
        println("No missing strings")
    }

    println("Done. Missing $safeSourceLangCode: $numMissingSourceLang; Missing $safeTargetLangCode: $numMissingTargetLang")
}

fun loadTranslationStrings(folder: String, lang: String): List<TranslationString> {
    val listOfTranslationStrings = mutableListOf<TranslationString>()
    val file = File(rootFolder, "${folder}${System.getProperty("file.separator")}${lang}.ini")
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

data class TranslationString(val section: String?, val key: String, val translation: String, val linenumber: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is TranslationString) { return false }
        return (other.hashCode() == this.hashCode())
    }

    override fun hashCode(): Int {
        var result = section?.hashCode() ?: 0
        result = 31 * result + key.hashCode()
        return result
    }
}