package com.example.twoforyou_botcscript.ui.character_list

import com.example.twoforyou_botcscript.data.model.Character

data class CharacterListUiState(
    val allCharactersList : List<Character> = emptyList(),
    val filteredCharactersList : List<Character> = emptyList()
)