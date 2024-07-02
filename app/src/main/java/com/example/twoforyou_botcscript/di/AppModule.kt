package com.example.twoforyou_botcscript.di

import com.example.twoforyou_botcscript.data.repository.display_script.ScriptDisplayRepositoryImpl
import com.example.twoforyou_botcscript.data.repository.script_info.ScriptInfoRepositoryImpl
import com.example.twoforyou_botcscript.domain.repository.display_script.ScriptDisplayRepository
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
    fun providesScriptDisplayRepository(): ScriptDisplayRepository {
        return ScriptDisplayRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesScriptInfoRepository(): ScriptInfoRepository {
        return ScriptInfoRepositoryImpl()
    }
}