package com.example.pokemontask.repository

import com.example.pokemontask.DB.PokemonDao
import com.example.pokemontask.apis.ApiHelper
import com.example.pokemontask.model.pokemons
import com.mindorks.framework.mvvm.utils.NetworkHelper
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class HomeRepository @Inject constructor( val networkChecker:NetworkHelper , val pokmonDb:PokemonDao , val pokemonApi :ApiHelper){

    suspend fun getdata(): Flow<pokemons> {
        return if (networkChecker.isNetworkConnected()){
            pokemonApi.getPokmons()
        } else pokmonDb.getPokemons()
    }
    suspend fun insertIntoDB(pokemons: pokemons)= pokmonDb.insertPokemons(pokemons)

}