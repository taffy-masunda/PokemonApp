package com.example.pokemonsplashactivity.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonsplashactivity.data.PokemonModel
import com.example.pokemonsplashactivity.data.PokemonRespository
import retrofit2.Call
import retrofit2.Response

class PokemonListViewModel constructor(private val repository: PokemonRespository) : ViewModel() {
    val pokemonList = MutableLiveData<PokemonModel>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun getAllPokemons() {
        val response = repository.getAllPokemons()
        response.enqueue(object : retrofit2.Callback<PokemonModel> {
            override fun onResponse(call: Call<PokemonModel>, response: Response<PokemonModel>) {
                pokemonList.postValue(response.body())
                loading.value = false
            }

            override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
                errorMessage.postValue(t.message)
                loading.value = false
            }
        })
    }
}