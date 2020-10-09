package com.example.pokemontask.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemontask.model.PokemonData
import com.example.pokemontask.model.pokemonList

import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemon :pokemonList)

    @Query("select * from PokemonData")
    suspend fun getPokemon():pokemonList
    @Query("select * from PokemonData")
    fun getPokemons():Flow<pokemonList>
}