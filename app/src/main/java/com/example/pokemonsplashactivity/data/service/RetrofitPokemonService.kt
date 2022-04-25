package com.example.pokemonsplashactivity.data.service

import com.example.pokemonsplashactivity.data.PokemonModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitPokemonService {
    @GET("https://pokeapi.co/api/v2/pokemon")
    fun getAllPokemons(): Call<PokemonModel>

    @GET("pokemon/{id}")
    fun getOnePokemon(@Path("id") id: Int): Call<PokemonModel>

    companion object {

        var retrofitService: RetrofitPokemonService? = null

        fun getInstance() : RetrofitPokemonService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitPokemonService::class.java)
            }
            return retrofitService!!
        }
    }
}