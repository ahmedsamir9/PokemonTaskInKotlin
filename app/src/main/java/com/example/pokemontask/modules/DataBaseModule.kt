package com.example.pokemontask.modules

import android.content.Context
import androidx.room.Room
import com.example.pokemontask.DB.PokemonDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun  dataBaseProvider(@ApplicationContext context: Context) =Room.databaseBuilder(context,PokemonDataBase::class.java,"PokemonDB").build()
    @Singleton
    @Provides
    fun provideDao(dataBase: PokemonDataBase)=dataBase.pokemoneDao()
}