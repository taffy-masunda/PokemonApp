package com.example.pokemonsplashactivity.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonsplashactivity.R
import com.example.pokemonsplashactivity.data.PokemonRespository
import com.example.pokemonsplashactivity.data.service.RetrofitPokemonService
import com.example.pokemonsplashactivity.databinding.ActivityPokemonDetailsBinding
import com.example.pokemonsplashactivity.viewmodel.PokemonListViewModel
import com.example.pokemonsplashactivity.viewmodel.PokemonViewModelFactory

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailsBinding
    private lateinit var viewModel: PokemonListViewModel
    private val retrofitService = RetrofitPokemonService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, PokemonViewModelFactory(PokemonRespository(retrofitService)))
            .get(PokemonListViewModel::class.java)

        viewModel.loading.observe(this) {
            if (it) {
                binding.loadingDialog.visibility = View.VISIBLE
            } else {
                binding.loadingDialog.visibility = View.GONE
                binding.pokemonDetailsContainer.visibility = View.VISIBLE
            }
        }
    }
}