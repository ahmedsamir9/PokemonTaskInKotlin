package com.example.pokemontask.ui.HomeFragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.pokemontask.model.pokemons
import com.example.pokemontask.repository.HomeRepository
import com.mindorks.framework.mvvm.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(val homeRepository: HomeRepository): ViewModel() ,LifecycleObserver{
    private val _pokmons = MutableLiveData<Resource<pokemons>>()
    val pokmonss: LiveData<Resource<pokemons>>
        get() = _pokmons

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun getPokemons(){
        viewModelScope.launch {
            homeRepository.getdata()
                .flowOn(Dispatchers.Default)
                .catch { error->_pokmons.postValue(Resource.error(error.localizedMessage,null)) }
                .collect { data-> _pokmons.postValue(Resource.success(data)) }
        }
    }


}