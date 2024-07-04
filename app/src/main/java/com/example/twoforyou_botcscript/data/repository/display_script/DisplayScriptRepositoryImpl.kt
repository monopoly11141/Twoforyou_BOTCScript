package com.example.twoforyou_botcscript.data.repository.display_script

import com.example.twoforyou_botcscript.data.db.local.ScriptDao
import com.example.twoforyou_botcscript.data.db.remote.FirebaseCharacterDatabase
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.data.model.Script
import com.example.twoforyou_botcscript.domain.repository.display_script.DisplayScriptRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisplayScriptRepositoryImpl @Inject constructor(
    private val scriptDao: ScriptDao,
    private val firebaseCharacterDatabase: FirebaseCharacterDatabase
) : DisplayScriptRepository {

    override fun getAllCharacters(): Flow<List<Character>> {
        return firebaseCharacterDatabase.getAllCharactersList()
    }

    override fun getAllScript(): Flow<List<Script>> {
        return scriptDao.getAllScript()
    }

    override suspend fun insertScript(script: Script) {
        scriptDao.insertScript(script)
    }

    override suspend fun deleteScript(script: Script) {
        scriptDao.deleteScript(script)
    }

    override suspend fun deleteAllScript() {
        scriptDao.deleteAllScript()
    }
}