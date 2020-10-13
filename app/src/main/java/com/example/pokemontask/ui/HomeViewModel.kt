package com.example.pokemontask.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.pokemontask.model.pokemonList
import com.example.pokemontask.repository.HomeRepository
import com.mindorks.framework.mvvm.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(val homeRepository: HomeRepository): ViewModel() ,LifecycleObserver{
    private val _pokmons = MutableLiveData<Resource<pokemonList>>()
    val pokmons: LiveData<Resource<pokemonList>>
        get() = _pokmons

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun getPokemons(){
        viewModelScope.launch {
            homeRepository.getDataFromDB()
                .flowOn(Dispatchers.Default)
                .flatMapConcat {pokmons->
                    if (pokmons!!.isEmpty()){
                    return@flatMapConcat homeRepository.getDataFromApi()
                } else {
                   return@flatMapConcat flow {
                       emit(pokmons)
                   }
                }
                }
                .catch { _pokmons.postValue(Resource.error(it.localizedMessage,null)) }
                .collect { _pokmons.postValue(Resource.success(it)) }
        }
    }


}