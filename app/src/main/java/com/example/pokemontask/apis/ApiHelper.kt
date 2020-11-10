package com.example.pokemontask.apis

import com.example.pokemontask.model.pokemonList

import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun getPokmons():pokemonList
}