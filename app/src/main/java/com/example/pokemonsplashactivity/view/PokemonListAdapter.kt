package com.example.pokemonsplashactivity.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonsplashactivity.data.Results
import com.example.pokemonsplashactivity.databinding.LayoutItemPokemonListBinding

class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    private var pokemonList = mutableListOf<Results>()
    fun setPokemonsList(pokemonList: List<Results>) {
        this.pokemonList = pokemonList.toMutableList()
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
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    class PokemonViewHolder(val binding: LayoutItemPokemonListBinding) :
        RecyclerView.ViewHolder(binding.root)
}