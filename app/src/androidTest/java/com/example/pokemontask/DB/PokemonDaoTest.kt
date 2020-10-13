package com.example.pokemontask.DB

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokemontask.model.PokemonData
import com.example.pokemontask.model.pokemonList
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.manuelvicnt.coroutinesflow.MainCoroutineRule
import com.manuelvicnt.coroutinesflow.runBlocking
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import org.junit.runner.RunWith
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class PokemonDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    lateinit var pokemonDataBase: PokemonDataBase
    lateinit var pokemonDao: PokemonDao
    @Before
    fun setup(){
        pokemonDataBase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext()
            ,PokemonDataBase::class.java).build()
        pokemonDao = pokemonDataBase.pokemoneDao()
    }
    @After
    fun cleanUp(){
        pokemonDataBase.close()
    }
    @Test
    fun insertDataIntoDatabase () = runBlockingTest{
        //arrange
        val insertedList = ArrayList<PokemonData>()
        insertedList.add(PokemonData("Peka", "http//"))
        insertedList.add(PokemonData("semaShor", "http//"))
        //act
        pokemonDao.insertPokemons(insertedList)
         var result :List<PokemonData>? = null
         async(Dispatchers.Unconfined) { pokemonDao.getPokemons().collect { result=it } }.cancel()
        //Assert
        assertThat(result).contains(PokemonData("Peka", "http//"))
        assertThat(result).contains(PokemonData("semaShor", "http//"))
    }

}