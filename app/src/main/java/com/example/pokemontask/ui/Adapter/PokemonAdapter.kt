package com.example.pokemontask.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontask.R
import com.example.pokemontask.databinding.PokemonitemBinding
import com.example.pokemontask.databinding.PokemonitemBindingImpl
import com.example.pokemontask.model.PokemonData

class PokemonAdapter() :
    ListAdapter<PokemonData, PokemonAdapter.PokemonViewHolder>(PokemonDataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PokemonitemBinding.inflate(layoutInflater, parent, false)
        return PokemonViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




    inner class PokemonViewHolder(
        val itemBinder: PokemonitemBinding
    ) : RecyclerView.ViewHolder(itemBinder.root) {



        fun bind(item: PokemonData) {
                itemBinder.pokemon=item
        }
    }


    private class PokemonDataDiffCallback : DiffUtil.ItemCallback<PokemonData>() {
        override fun areItemsTheSame(
            oldItem: PokemonData,
            newItem: PokemonData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PokemonData,
            newItem: PokemonData
        ): Boolean {
          return oldItem.name == newItem.name
        }
    }
}