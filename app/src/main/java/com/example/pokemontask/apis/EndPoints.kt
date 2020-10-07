package com.example.pokemontask.apis

import com.example.pokemontask.model.PokemonResponse
import retrofit2.http.GET

interface EndPoints {
    @GET("pokemon")
    suspend fun getPokmons():PokemonResponse

}