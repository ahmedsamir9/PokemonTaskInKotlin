package com.example.pokemontask.apis

import com.example.pokemontask.model.pokemonList

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiHelperImp @Inject constructor(val endPoint: EndPoints):
    ApiHelper {
    override suspend fun getPokmons(): Flow<pokemonList> = flow {
        emit(endPoint.getPokmons().results)
    }
}