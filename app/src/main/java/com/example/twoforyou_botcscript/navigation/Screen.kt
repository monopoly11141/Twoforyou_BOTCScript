package com.example.twoforyou_botcscript.navigation

import kotlinx.serialization.Serializable

sealed class Screen() {
    @Serializable
    object DisplayScriptScreen

    @Serializable
    data class ScriptInfoScreen(
        val scriptId: Int = -1
    )
}