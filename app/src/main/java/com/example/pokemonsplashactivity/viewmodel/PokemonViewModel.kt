package com.example.pokemonsplashactivity.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonsplashactivity.data.PokemonDetailsResponse
import com.example.pokemonsplashactivity.data.PokemonResponse
import com.example.pokemonsplashactivity.data.PokemonRespository
import retrofit2.Call
import retrofit2.Response

class PokemonViewModel constructor(private val repository: PokemonRespository) : ViewModel() {
    val pokemonList = MutableLiveData<PokemonResponse>()
    val singlePokemon = MutableLiveData<PokemonDetailsResponse>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val loaded = MutableLiveData<Boolean>()

    fun getAllPokemons() {
        val response = repository.getAllPokemons()
        response.enqueue(object : retrofit2.Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                pokemonList.postValue(response.body())
                loaded.value = true
                loading.value = false
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
                loaded.value = false
                loading.value = false
            }
        })
    }

    fun getOnePokemon(pokemonId : Int) {
        val response = repository.getOnePokemon(pokemonId)
        response.enqueue(object : retrofit2.Callback<PokemonDetailsResponse> {
            override fun onResponse(call: Call<PokemonDetailsResponse>, response: Response<PokemonDetailsResponse>) {
                singlePokemon.postValue(response.body())
                loaded.value = true
                loading.value = false
            }

            override fun onFailure(call: Call<PokemonDetailsResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
                loaded.value = false
                loading.value = false
            }
        })
    }
}