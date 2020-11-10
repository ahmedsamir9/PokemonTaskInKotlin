package com.example.pokemontask.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.pokemontask.model.pokemonList
import com.example.pokemontask.repository.HomeRepository
import com.example.pokemontask.ui.states.HomeEventState
import com.example.pokemontask.ui.states.HomeStateView
import com.mindorks.framework.mvvm.utils.Resource
import com.mindorks.framework.mvvm.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Error

@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(val homeRepository: HomeRepository): ViewModel() ,LifecycleObserver{
    private val _pokmons = MutableStateFlow<HomeStateView>(HomeStateView.Idle)
    val pokmons: StateFlow<HomeStateView>
        get() = _pokmons
val events = Channel<HomeEventState>(capacity = Channel.UNLIMITED)

    suspend fun getPokemons(){
        _pokmons.value = HomeStateView.Loading
        homeRepository.getPokemons().flowOn(Dispatchers.IO).collect {
            when(it.status){
                Status.ERROR -> _pokmons.value = HomeStateView.Error(it.message.toString())
                Status.SUCCESS -> _pokmons.value= HomeStateView.Pokemons(data = it.data)
            }
        }
    }
 @OnLifecycleEvent(Lifecycle.Event.ON_START)
private fun handleEvents(){
    viewModelScope.launch {
        events.consumeAsFlow().collect{
            when(it){
                is HomeEventState.FeatchPokemons->getPokemons()
            }
        }
    }

}

}