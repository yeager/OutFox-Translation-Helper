package util

import model.TranslationString

var translationStrings: List<TranslationString>? = null
var userLanguage = "nl" // "en"

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
    translationStrings = loadTranslationStrings(text)
}