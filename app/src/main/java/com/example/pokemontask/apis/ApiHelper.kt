package com.example.pokemontask.apis

import com.example.pokemontask.model.pokemons
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun getPokmons():Flow<pokemons>
}