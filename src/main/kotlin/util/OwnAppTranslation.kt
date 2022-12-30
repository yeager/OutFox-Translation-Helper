package com.tinyfoxes.translationhelper.util
import com.tinyfoxes.translationhelper.appLanguage
import com.tinyfoxes.translationhelper.model.TranslationString

var translationStrings: List<TranslationString>? = null

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
    val text = {}::class.java.classLoader.getResource("$appLanguage.ini")?.readText() ?: return
    translationStrings = Util.loadTranslationStrings(text)
}