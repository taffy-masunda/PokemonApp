package com.example.pokemonsplashactivity.data

import com.example.pokemonsplashactivity.data.service.RetrofitPokemonService

class PokemonRespository constructor(private val retrofitPokemonService: RetrofitPokemonService) {
    fun getAllPokemons() = retrofitPokemonService.getAllPokemons()
}