package com.example.twoforyou_botcscript.data.repository.character_list

import com.example.twoforyou_botcscript.data.db.remote.FirebaseCharacterDatabase
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.domain.repository.character_list.CharacterListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterListRepositoryImpl @Inject constructor(
    private val firebaseCharacterDatabase: FirebaseCharacterDatabase
) : CharacterListRepository {
    override fun getAllCharacters() : Flow<List<Character>> {
        return firebaseCharacterDatabase.getAllCharactersList()
    }

}