package com.example.pokemonsplashactivity.data

data class PokemonResponse(
    val count: Long,
    val next: String,
    val previous: Any? = null,
    val results: List<Results>
)

data class Results(
    val name: String,
    val url: String
)