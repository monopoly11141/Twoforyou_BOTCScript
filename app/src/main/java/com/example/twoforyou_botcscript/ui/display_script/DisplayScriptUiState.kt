package com.example.twoforyou_botcscript.ui.display_script

import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.data.model.Script

data class DisplayScriptUiState(
    val allCharactersList : List<Character> = emptyList(),
    val scriptList: List<Script> = emptyList()
)