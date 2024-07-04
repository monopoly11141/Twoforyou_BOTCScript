package com.example.twoforyou_botcscript.util

fun String.getEnglish() : String {
    return this.filter {
        "^[A-Za-z]*$".toRegex().containsMatchIn(it.toString())
    }.replace("_", " ").trim()
}

fun String.getKorean() : String {
    return this.filter {
        "^[ㄱ-ㅎ가-힣]*$".toRegex().containsMatchIn(it.toString())
    }.replace("_", " ").trim()
}
