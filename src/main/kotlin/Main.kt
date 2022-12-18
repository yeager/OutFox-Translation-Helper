import util.loadTranslationStrings
import util.s
import java.io.File
import kotlin.system.exitProcess

var rootFolder: File? = null //File("/Users/frankbouwens/priv/Outfox/Tiny-Foxes/OutFox-NL/")
var sourceLangCode: String? = null //"en"
var targetLangCode: String? = null //"nl"

fun main(args: Array<String>) {
    println(s("[general]Translation Helper for Project OutFox; by FrankkieNL"))

    while (true) {
        startMenu()
    }
}

fun startMenu() {
    println(
        """     
        ${s("[cli]Menu:")}
        0) ${s("[cli]Exit")}
        1) ${s("[cli]Check for missing keys")}
    """.trimIndent()
    )
    val input = readln()
    if (input.toIntOrNull() != null) {
        when (input.toInt()) {
            0 -> exitProcess(0)
            1 -> checkForMissingKeys()
            else -> println(s("[cli]Number not found"))
        }
    }
}

fun askRootFolder() {
    println(s("[cli]Please provide the root folder for the translations: (full path) (e.g. /Users/frankbouwens/priv/Outfox/Tiny-Foxes/OutFox-NL/ )"))
    val input = readln()
    val tempFile = File(input)
    if (!tempFile.exists()) {
        println(s("[cli]File does not exist"))
        return
    }
    if (!tempFile.canRead()) {
        println(s("[cli]File cannot be read"))
        return
    }
    if (!tempFile.isDirectory) {
        println(s("[cli]File is not a directory"))
        return
    }
    //All ok
    rootFolder = tempFile
    rootFolder?.let { safeRootFolder ->
        println(String.format(s("[cli]Set as root folder: %s"), safeRootFolder.absolutePath))
    }
}

fun askLangCode(prompt: String): String? {
    println(prompt)
    val langCode = readln().lowercase()
    if (langCode.length != 2 || !"\\w\\w".toRegex().matches(langCode)) {
        println(String.format(s("[cli]That is not a 2 letter language code %s."),langCode))
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
        sourceLangCode = askLangCode(s("[cli]Please enter source language (as 2-letter code, e.g. 'en')")) ?: return
    }
    val safeSourceLangCode = sourceLangCode ?: return

    if (targetLangCode == null) {
        targetLangCode = askLangCode(s("[cli]Please enter target language (as 2-letter code, e.g. 'nl')")) ?: return
    }
    val safeTargetLangCode = targetLangCode ?: return

    val sourceStrings = loadTranslationStrings("_fallback", safeSourceLangCode)
    val targetStrings = loadTranslationStrings("_fallback", safeTargetLangCode)

    var numMissingSourceLang = 0
    var numMissingTargetLang = 0
    println(s("Checking for missing keys..."))
    println(String.format(s("[cli]%s strings missing in the %s strings: "), safeSourceLangCode, safeTargetLangCode))
    sourceStrings.forEach { sourceString ->
        if (!targetStrings.contains(sourceString)) {
            println("L${sourceString.linenumber} [${sourceString.section}] ${sourceString.key}=${sourceString.translation}")
            numMissingSourceLang++
        }
    }
    if (numMissingSourceLang == 0) {
        println(s("[cli]No missing strings"))
    }

    println(String.format(s("[cli]%s strings missing in the %s strings: "), safeTargetLangCode, safeSourceLangCode))
    targetStrings.forEach { targetString ->
        if (!sourceStrings.contains(targetString)) {
            println("L${targetString.linenumber} [${targetString.section}] ${targetString.key}=${targetString.translation}")
            numMissingTargetLang++
        }
    }
    if (numMissingTargetLang == 0) {
        println(s("[cli]No missing strings"))
    }

    println(String.format(s("[cli]Done. Missing %s: %d; Missing %s: %d"),safeSourceLangCode, numMissingSourceLang, safeTargetLangCode, numMissingTargetLang))
}
