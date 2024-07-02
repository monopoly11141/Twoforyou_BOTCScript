package com.example.twoforyou_botcscript.domain.repository.display_script

import com.example.twoforyou_botcscript.data.model.Character
import kotlinx.coroutines.flow.Flow

interface DisplayScriptRepository {
    fun getAllCharacters() : Flow<List<Character>>

}