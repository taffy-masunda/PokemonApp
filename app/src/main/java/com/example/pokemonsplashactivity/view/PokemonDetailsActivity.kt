package com.example.pokemonsplashactivity.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonsplashactivity.R
import com.example.pokemonsplashactivity.data.PokemonDetailsResponse
import com.example.pokemonsplashactivity.data.PokemonRespository
import com.example.pokemonsplashactivity.data.service.RetrofitPokemonService
import com.example.pokemonsplashactivity.databinding.ActivityPokemonDetailsBinding
import com.example.pokemonsplashactivity.viewmodel.PokemonViewModel
import com.example.pokemonsplashactivity.viewmodel.PokemonViewModelFactory

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailsBinding
    private lateinit var viewModel: PokemonViewModel
    private val retrofitService = RetrofitPokemonService.getInstance()
    private var pokemonId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemonId = extractPokemonId(intent.getStringExtra(URL_KEY).toString())


        viewModel = ViewModelProvider(this, PokemonViewModelFactory(PokemonRespository(retrofitService, pokemonId), pokemonId))
            .get(PokemonViewModel::class.java)

        viewModel.loading.observe(this) {
            if (it) {
                binding.loadingDialog.visibility = View.VISIBLE
            } else {
                binding.loadingDialog.visibility = View.GONE
                binding.pokemonDetailsContainer.visibility = View.VISIBLE
            }
        }

        viewModel.singlePokemon.observe(this) {
            val poke: PokemonDetailsResponse = it
            binding.pokemoneNameTextview.text = poke.name
        }

        viewModel.getOnePokemon(pokemonId)
    }

    private fun extractPokemonId(pokemonUrl: String): Int {
        // to remove the last forward-slash from the URL
        val pokemonUrlNew = pokemonUrl.removeRange((pokemonUrl.length-1), pokemonUrl.length)
        return pokemonUrlNew.substringAfterLast("/", "").toInt()
    }

    companion object{
        const val URL_KEY = "pokemonURL"
    }
}