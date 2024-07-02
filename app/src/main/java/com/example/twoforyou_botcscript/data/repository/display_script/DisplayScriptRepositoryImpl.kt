package com.example.twoforyou_botcscript.data.repository.display_script

import com.example.twoforyou_botcscript.data.db.remote.FirebaseCharacterDatabase
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.domain.repository.display_script.DisplayScriptRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisplayScriptRepositoryImpl @Inject constructor(
    private val firebaseCharacterDatabase: FirebaseCharacterDatabase
): DisplayScriptRepository {

    override fun getAllCharacters() : Flow<List<Character>> {
        return firebaseCharacterDatabase.getAllCharactersList()
    }


}