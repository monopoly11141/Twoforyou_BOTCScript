package com.example.twoforyou_botcscript.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.example.twoforyou_botcscript.data.model.Character

class CharactersObjectListConverter {

    @TypeConverter
    fun listToJson(value: List<Character>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Character>? {
        return Gson().fromJson(value, Array<Character>::class.java)?.toList()
    }
}