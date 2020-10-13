package com.example.pokemontask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.pokemontask.R
import com.example.pokemontask.databinding.ActivityMainBinding
import com.example.pokemontask.ui.Adapter.PokemonAdapter
import com.mindorks.framework.mvvm.utils.Status

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binder :ActivityMainBinding
     private val homeViewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this ,R.layout.activity_main)
        lifecycle.addObserver(homeViewModel)
        subscribeOnLiveData()
    }


    private fun  subscribeOnLiveData(){
        homeViewModel.pokmons.observe(this , Observer {
            when(it.status){
                Status.SUCCESS->{
                    binder.mainProgressBar.visibility = View.GONE
                    binder.rvPokemons.apply {
                        visibility =View.VISIBLE
                        layoutManager = LinearLayoutManager(this@MainActivity)
                      val pokemonAdapter = PokemonAdapter()
                        pokemonAdapter.submitList(it.data)
                        adapter =pokemonAdapter
                    }
                }
                Status.LOADING->{
                    hideView()
                }
                Status.ERROR->{
                   hideView()
                    Toast.makeText(this,"Error${it.message}",Toast.LENGTH_LONG).show()
                }
            }

        })
    }
    private fun hideView(){
        binder.mainProgressBar.visibility = View.GONE
        binder.rvPokemons.visibility = View.GONE
    }
}