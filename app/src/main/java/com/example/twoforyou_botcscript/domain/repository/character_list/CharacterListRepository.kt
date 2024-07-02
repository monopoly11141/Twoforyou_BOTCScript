package com.example.twoforyou_botcscript.domain.repository.character_list

import com.example.twoforyou_botcscript.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterListRepository {
    fun getAllCharacters() : Flow<List<Character>>
}