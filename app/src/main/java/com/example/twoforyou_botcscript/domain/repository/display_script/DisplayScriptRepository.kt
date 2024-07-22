package com.example.twoforyou_botcscript.domain.repository.display_script

import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.data.model.Script
import kotlinx.coroutines.flow.Flow

interface DisplayScriptRepository {
    fun getAllCharacters() : Flow<List<Character>>

    fun getAllScript() : Flow<List<Script>>
    suspend fun insertScript(script: Script)
    suspend fun updateScript(script: Script)
    suspend fun deleteScript(script: Script)
    suspend fun deleteAllScript()
}