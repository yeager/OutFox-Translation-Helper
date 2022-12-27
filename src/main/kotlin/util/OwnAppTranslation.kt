package com.tinyfoxes.translationhelper.util
import com.tinyfoxes.translationhelper.model.TranslationString

var translationStrings: List<TranslationString>? = null
var userLanguage = "en" // "nl"

fun s(translationString: String): String {
    if (translationStrings == null) {
        initTranslations()
    }
    translationStrings?.let { safeList ->
        val t = safeList.firstOrNull { it.toId() == translationString }
        if (t != null) {
            return t.translation
        }
    }
    return translationString
}

fun initTranslations() {
    val text = {}::class.java.classLoader.getResource("$userLanguage.ini")?.readText() ?: return
    translationStrings = Util.loadTranslationStrings(text)
}