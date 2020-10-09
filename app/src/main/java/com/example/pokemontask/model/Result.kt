package com.example.pokemontask.model


import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
@Entity
data class PokemonData(
    @PrimaryKey
    @NonNull
    @Json(name = "name")
    var name: String,
    @Json(name = "url")
    var url: String?
)

typealias pokemonList = List<PokemonData>?