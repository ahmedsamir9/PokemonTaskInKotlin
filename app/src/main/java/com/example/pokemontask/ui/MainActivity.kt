package com.example.pokemontask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.pokemontask.R
import com.example.pokemontask.databinding.ActivityMainBinding
import com.example.pokemontask.ui.Adapter.PokemonAdapter
import com.example.pokemontask.ui.states.HomeEventState
import com.example.pokemontask.ui.states.HomeStateView
import com.mindorks.framework.mvvm.utils.Status

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binder :ActivityMainBinding
     private val homeViewModel : HomeViewModel by viewModels()
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this ,R.layout.activity_main)
        lifecycle.addObserver(homeViewModel)
        subscribeOnLiveData()
        homeViewModel.events.offer(HomeEventState.FeatchPokemons)
    }


    private fun  subscribeOnLiveData(){
        lifecycleScope.launch{
            homeViewModel.pokmons.collectLatest {
                when(it){
                   is HomeStateView.Loading -> {
                       makeToast("loading....")
                       binder.mainProgressBar.visibility = View.VISIBLE
                   }
                    is HomeStateView.Error ->{
                        makeToast(message = it.message)
                        hideView()}
                    is HomeStateView.Pokemons -> {
                        binder.mainProgressBar.visibility = View.GONE
                        binder.rvPokemons.apply {
                            visibility =View.VISIBLE
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            val pokemonAdapter = PokemonAdapter()
                            pokemonAdapter.submitList(it.data)
                            adapter =pokemonAdapter
                        }
                    }
                }
            }
        }
        }
    private fun hideView(){
        binder.mainProgressBar.visibility = View.GONE
        binder.rvPokemons.visibility = View.GONE
    }
    private fun makeToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}