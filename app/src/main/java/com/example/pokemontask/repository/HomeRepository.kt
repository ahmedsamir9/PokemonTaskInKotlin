package com.example.pokemontask.repository

import NetworkBoundResource
import android.util.Log
import com.example.movieapp.utils.ApiResult
import com.example.movieapp.utils.safeApiCall
import com.example.pokemontask.DB.PokemonDao
import com.example.pokemontask.apis.ApiHelper
import com.example.pokemontask.model.PokemonData
import com.example.pokemontask.model.pokemonList
import com.mindorks.framework.mvvm.utils.NetworkHelper
import com.mindorks.framework.mvvm.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class HomeRepository @Inject constructor( val networkChecker:NetworkHelper , val pokmonDb:PokemonDao , val pokemonApi :ApiHelper){

//    suspend fun getDataFromDB(): Flow<pokemonList> {
//       return pokmonDb.getPokemons()
//    }
//    suspend fun getDataFromApi(): Flow<pokemonList> {
//        return pokemonApi.getPokmons().map {pokemons->
//            val newPokmonlist  = ArrayList<PokemonData>()
//            if (pokemons != null) {
//                for (pokmon in pokemons)
//                {
//                   val splitaedList = pokmon.url?.split("/")
//
//                    pokmon.url = "https://pokeres.bastionbot.org/images/pokemon" +
//                            "/${splitaedList?.get(6)}.png"
//                    newPokmonlist.add(pokmon)
//                }
//            }
//            pokmonDb.insertPokemons(newPokmonlist)
//            newPokmonlist
//        }
//    }
   suspend fun getPokemons(): Flow<Resource<pokemonList>> {
        return object :NetworkBoundResource<pokemonList,pokemonList>(){
            override suspend fun saveNetworkResult(item: pokemonList) {
                val newPokmonlist = ArrayList<PokemonData>()
                if (item != null) {
                    for (pokmon in item) {
                        val splitaedList = pokmon.url?.split("/")

                        pokmon.url = "https://pokeres.bastionbot.org/images/pokemon" +
                                "/${splitaedList?.get(6)}.png"
                        newPokmonlist.add(pokmon)
                    }
                }
                pokmonDb.insertPokemons(newPokmonlist)

            }



            override fun loadFromDb(): Flow<pokemonList> {

                return pokmonDb.getPokemons()

            }

            override suspend fun fetchFromNetwork(): ApiResult<pokemonList> {

                return safeApiCall( apiCall = {pokemonApi.getPokmons()})
            }

            override fun shouldFetch(data: pokemonList?): Boolean {
                return data ==null || data.isEmpty()
            }
        }.asFlow()
    }

}