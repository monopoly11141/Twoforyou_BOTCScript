package com.example.twoforyou_botcscript.di

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
        firebaseCharacterDatabase: FirebaseCharacterDatabase
    ): DisplayScriptRepository {
        return DisplayScriptRepositoryImpl(firebaseCharacterDatabase)
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
}