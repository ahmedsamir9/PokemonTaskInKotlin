package com.example.pokemontask.apis

import com.example.pokemontask.model.pokemons
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiHelperImp @Inject constructor(val endPoint: EndPoints):
    ApiHelper {
    override suspend fun getPokmons(): Flow<pokemons> = flow {
        emit(endPoint.getPokmons().results)
    }
}