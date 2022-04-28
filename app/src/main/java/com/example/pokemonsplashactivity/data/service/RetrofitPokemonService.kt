package com.example.pokemonsplashactivity.data.service

import com.example.pokemonsplashactivity.data.PokemonDetailsResponse
import com.example.pokemonsplashactivity.data.PokemonResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitPokemonService {
    @GET("pokemon")
    fun getAllPokemons(): Call<PokemonResponse>

    @GET("pokemon/{id}")
    fun getOnePokemon(@Path("id") id: Int): Call<PokemonDetailsResponse>

    companion object {

        private var retrofitService: RetrofitPokemonService? = null

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