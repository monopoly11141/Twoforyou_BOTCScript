package com.example.twoforyou_botcscript.di

import android.content.Context
import androidx.room.Room
import com.example.twoforyou_botcscript.data.db.local.ScriptDao
import com.example.twoforyou_botcscript.data.db.local.ScriptDb
import com.example.twoforyou_botcscript.data.db.remote.FirebaseCharacterDatabase
import com.example.twoforyou_botcscript.data.repository.character_list.CharacterListRepositoryImpl
import com.example.twoforyou_botcscript.data.repository.display_script.DisplayScriptRepositoryImpl
import com.example.twoforyou_botcscript.data.repository.script_info.ScriptInfoRepositoryImpl
import com.example.twoforyou_botcscript.domain.repository.character_list.CharacterListRepository
import com.example.twoforyou_botcscript.domain.repository.display_script.DisplayScriptRepository
import com.example.twoforyou_botcscript.domain.repository.script_info.ScriptInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesCharacterListRepository(
        firebaseCharacterDatabase: FirebaseCharacterDatabase
    ): CharacterListRepository {
        return CharacterListRepositoryImpl(firebaseCharacterDatabase)
    }

    @Provides
    @Singleton
    fun providesScriptDisplayRepository(
        scriptDao : ScriptDao,
        firebaseCharacterDatabase: FirebaseCharacterDatabase
    ): DisplayScriptRepository {
        return DisplayScriptRepositoryImpl(scriptDao, firebaseCharacterDatabase)
    }

    @Provides
    @Singleton
    fun providesScriptInfoRepository(): ScriptInfoRepository {
        return ScriptInfoRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesFirebaseCharacterDatabase(): FirebaseCharacterDatabase {
        return FirebaseCharacterDatabase()
    }

    @Provides
    fun providesScriptDao(scriptDb: ScriptDb): ScriptDao = scriptDb.scriptDao

    @Provides
    @Singleton
    fun providesScriptDb(@ApplicationContext context: Context): ScriptDb =
        Room.databaseBuilder(context, ScriptDb::class.java, "script_database")
            .fallbackToDestructiveMigration()
            .build()
}