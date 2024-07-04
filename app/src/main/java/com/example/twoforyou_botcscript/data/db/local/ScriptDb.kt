package com.example.twoforyou_botcscript.data.db.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.twoforyou_botcscript.data.db.converter.CharacterListConverter
import com.example.twoforyou_botcscript.data.db.converter.CharactersObjectListConverter
import com.example.twoforyou_botcscript.data.model.Script

@Database(
    entities = [Script::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(
    CharacterListConverter::class,
    CharactersObjectListConverter::class,
)
abstract class ScriptDb : RoomDatabase() {
    abstract val scriptDao: ScriptDao

}