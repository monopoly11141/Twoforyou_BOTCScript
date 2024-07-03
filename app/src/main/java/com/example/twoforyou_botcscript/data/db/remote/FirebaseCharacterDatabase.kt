package com.example.twoforyou_botcscript.data.db.remote

import android.util.Log
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.data.model.helper.Character_Type
import com.example.twoforyou_botcscript.data.model.helper.Script_List
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FirebaseCharacterDatabase {
    private var characterDatabase: DatabaseReference = Firebase.database.reference
    private val characterList = MutableStateFlow<List<Character>>(emptyList())
    private lateinit var character: Character

    fun getAllCharactersList(): Flow<List<Character>> {
        for (script in Script_List.entries) {
            for (characterType in Character_Type.entries) {
                val characterDbRef = characterDatabase.child(script.toString())
                    .child(characterType.toString())

                characterDbRef.get().addOnSuccessListener { dataSnapshot ->
                    for (characterDataSnapshot in dataSnapshot.children) {
                        character =
                            characterDataSnapshot.getValue(Character::class.java)!!
                        characterList.value += character
                    }
                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }

            }
        }
        return characterList
    }

}

