package com.example.pokemonsplashactivity.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonsplashactivity.R
import com.example.pokemonsplashactivity.data.PokemonRespository
import com.example.pokemonsplashactivity.data.service.RetrofitPokemonService
import com.example.pokemonsplashactivity.databinding.ActivityMainBinding
import com.example.pokemonsplashactivity.viewmodel.PokemonListViewModel
import com.example.pokemonsplashactivity.viewmodel.PokemonViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PokemonListViewModel
    private val retrofitService = RetrofitPokemonService.getInstance()
    private val adapter = PokemonListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, PokemonViewModelFactory(PokemonRespository(retrofitService)))
                .get(PokemonListViewModel::class.java)

        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.loadingDialog.visibility = View.VISIBLE
            } else {
                    binding.loadingDialog.visibility = View.GONE
                    viewModel.loaded.observe(this) { loadedData ->
                    if (loadedData) {
                        binding.pokemonsRecyclerView.visibility = View.VISIBLE
                    } else {
                        binding.errorMessageTextview.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, "Error fetching Pokemon data!", Toast.LENGTH_SHORT).show()
        }

        binding.pokemonsRecyclerView.adapter = adapter
        viewModel.pokemonList.observe(this) {
            adapter.setPokemonsList(it.results)
        }

        viewModel.getAllPokemons()

    }
}