package com.example.twoforyou_botcscript.navigation

import kotlinx.serialization.Serializable

sealed class Screen() {
    @Serializable
    object DisplayScriptScreen

    @Serializable
    data class ScriptDetailScreen(
        val scriptId: Int = -1
    )

    @Serializable
    object CharacterListScreen
}