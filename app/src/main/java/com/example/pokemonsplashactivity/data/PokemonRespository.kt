package com.example.pokemonsplashactivity.data

import com.example.pokemonsplashactivity.data.service.RetrofitPokemonService

class PokemonRespository constructor(private val retrofitPokemonService: RetrofitPokemonService, private val pokemonId: Int) {
    fun getAllPokemons() = retrofitPokemonService.getAllPokemons()
    fun getOnePokemon(pokemonId: Int) = retrofitPokemonService.getOnePokemon(this.pokemonId)
}