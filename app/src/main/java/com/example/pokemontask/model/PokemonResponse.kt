package com.example.pokemontask.model


import com.squareup.moshi.Json

data class PokemonResponse(
    @Json(name = "count")
    var count: Int?,
    @Json(name = "next")
    var next: String?,
    @Json(name = "previous")
    var previous: String?,
    @Json(name = "results")
    var results: List<PokemonData>
)