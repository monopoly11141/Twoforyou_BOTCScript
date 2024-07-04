package com.example.twoforyou_botcscript.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

class CharacterListConverter {

    @TypeConverter
    fun listToJson(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        return Gson().fromJson(value, Array<String>::class.java)?.toList()
    }

}