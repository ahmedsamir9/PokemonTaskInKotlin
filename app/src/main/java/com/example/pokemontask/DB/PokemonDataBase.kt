package com.example.pokemontask.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokemontask.model.PokemonData

@Database(entities = arrayOf(PokemonData::class),version = 1,exportSchema = false)
abstract class PokemonDataBase : RoomDatabase() {
    abstract fun pokemoneDao():PokemonDao

}