package com.example.pokemontask.ui.states

import com.example.pokemontask.model.pokemonList

sealed class HomeStateView {
     object Loading:HomeStateView()
     class Error(val message:String):HomeStateView()
     data class Pokemons(val data:pokemonList):HomeStateView()
     object Idle:HomeStateView()
}