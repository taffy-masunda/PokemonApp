package com.example.pokemonsplashactivity.view

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonsplashactivity.data.Results
import com.example.pokemonsplashactivity.databinding.LayoutItemPokemonListBinding

class PokemonListAdapter(var passedInterface: PokemonRecyclerInterface) :
    RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    lateinit var recyclerInterface: PokemonRecyclerInterface

    private var pokemonList = mutableListOf<Results>()
    fun setPokemonsList(pokemonList: List<Results>) {
        this.pokemonList = pokemonList.toMutableList()
        this.recyclerInterface = passedInterface
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemPokemonListBinding.inflate(inflater, parent, false)

        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.binding.pokemonNameTextview.text = pokemon.name

        val activity = holder.itemView.context as Activity
        holder.binding.root.setOnClickListener { 
            if (position != RecyclerView.NO_POSITION) {
                val openDetailsIntent = Intent(activity, PokemonDetailsActivity::class.java)
                openDetailsIntent.putExtra(URL_KEY, pokemon.url)
                activity.startActivity(openDetailsIntent)
            }
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    class PokemonViewHolder(val binding: LayoutItemPokemonListBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object{
        const val URL_KEY = "pokemonURL"
    }
}