package com.example.twoforyou_botcscript.data.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.twoforyou_botcscript.data.model.helper.Script_General_Info

@Entity(tableName = "script_database")
data class Script(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Embedded
    val scriptGeneralInfo: Script_General_Info? = null,
    val charactersList: MutableList<String> = mutableListOf(),
    val charactersObjectList: MutableList<Character> = mutableListOf(),
   // val fabledCharacterList: MutableList<FabledCharacter> = mutableListOf()
)