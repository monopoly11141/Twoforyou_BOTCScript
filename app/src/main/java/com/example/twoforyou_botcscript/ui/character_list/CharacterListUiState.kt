package com.example.twoforyou_botcscript.ui.character_list

import com.example.twoforyou_botcscript.data.model.Character

data class CharacterListUiState(
    val allCharactersList: Set<Character> = emptySet(),
    val filteredCharactersList: Set<Character> = emptySet()
)