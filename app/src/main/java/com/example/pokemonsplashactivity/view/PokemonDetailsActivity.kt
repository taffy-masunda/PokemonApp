package com.example.pokemonsplashactivity.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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
    private var pokemonId: Int = 0
    private var types: String = ""
    private var moves: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back nav
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (!intent.getStringExtra(URL_KEY).isNullOrEmpty()) {
            pokemonId = extractPokemonId(intent.getStringExtra(URL_KEY).toString())
        }

        viewModel = ViewModelProvider(
            this,
            PokemonViewModelFactory(PokemonRespository(retrofitService, pokemonId))
        )
            .get(PokemonViewModel::class.java)

        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.loadingDialog.visibility = View.VISIBLE
            } else {
                binding.loadingDialog.visibility = View.GONE
                viewModel.loaded.observe(this) { loadedData ->
                    if (loadedData) {
                        binding.pokemonDetailsContainer.visibility = View.VISIBLE
                    } else {
                        binding.errorMessageTextview.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, "Error fetching Pokemon data!", Toast.LENGTH_SHORT).show()
        }

        viewModel.singlePokemon.observe(this) {
            setPokemonDetailsViews(it)
        }

        viewModel.getOnePokemon(pokemonId)
    }

    /* remove the last forward-slash from the URL */
    private fun extractPokemonId(pokemonUrl: String): Int {
        val pokemonUrlNew = pokemonUrl.removeRange((pokemonUrl.length - 1), pokemonUrl.length)
        return pokemonUrlNew.substringAfterLast("/", "").toInt()
    }

    private fun setPokemonDetailsViews(pokemon: PokemonDetailsResponse?) {
        if (pokemon != null) {
            binding.pokemoneNameTextview.text = pokemon.name

            Glide.with(this)
                .load(pokemon.sprites.frontDefault)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .centerCrop()
                .into(binding.pokemonImageview)

            if (pokemon.types.isNotEmpty()) {
                for (i in 0 until pokemon.types.size) { types += pokemon.types[i].type.name + ", " }
            }

            if (pokemon.moves.isNotEmpty()) {
                for (i in 0 until pokemon.moves.size) {
                    moves += pokemon.moves[i].move.name + ", "
                    if(i > 5){
                        break
                    }
                }
            }

            binding.heightTextview.text = resources.getString(R.string.height, pokemon.height)
            binding.weightTextview.text = resources.getString(R.string.height, pokemon.weight)
            binding.typesTextview.text = resources.getString(R.string.pokemonTypes, types)
            binding.movesTextview.text = resources.getString(R.string.pokemonMoves, moves)

            Glide.with(this)
                .load(pokemon.sprites.backDefault)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .centerCrop()
                .into(binding.pokemonBackImageview)

            Glide.with(this)
                .load(pokemon.sprites.frontShiny)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .centerCrop()
                .into(binding.pokemonShinyImageview)
        }
    }

    companion object {
        const val URL_KEY = "pokemonURL"
    }
}