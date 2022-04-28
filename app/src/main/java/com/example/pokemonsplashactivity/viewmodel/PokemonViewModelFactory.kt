package com.example.pokemonsplashactivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonsplashactivity.data.PokemonRespository

class PokemonViewModelFactory constructor(private val repository: PokemonRespository, private val pokemonId : Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            PokemonViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("PokemonListViewModel Not Found")
        }
    }
}