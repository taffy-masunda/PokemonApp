package com.example.pokemonsplashactivity.data

import com.google.gson.annotations.SerializedName

data class PokemonModel(
    val count: Long,
    val next: String,
    val previous: Any? = null,
    @SerializedName("results")
    val results: List<Results>
)

data class Results(
    val name: String,
    val url: String
)

