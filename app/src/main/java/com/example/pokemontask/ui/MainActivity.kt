package com.example.pokemontask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer

import com.example.pokemontask.R
import com.example.pokemontask.ui.HomeFragment.HomeViewModel
import com.mindorks.framework.mvvm.utils.Resource
import com.mindorks.framework.mvvm.utils.Status

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
     private val homeViewModel :HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(homeViewModel)
        subscribeOnLiveData()
    }


    private fun  subscribeOnLiveData(){
        homeViewModel.pokmonss.observe(this , Observer {
            when(it.status){
                Status.SUCCESS->Toast.makeText(this,it.data!!.get(0).name,Toast.LENGTH_LONG).show()
            }

        })
    }
}