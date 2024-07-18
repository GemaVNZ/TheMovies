package com.example.themovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.data.Movies
import com.example.themovies.databinding.ItemMoviesBinding
import com.squareup.picasso.Picasso

class MoviesAdapter (private var dataSet : List <Movies> = emptyList(),
                     private val onItemClickListener:(Int) -> Unit ):
        RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
            val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MoviesViewHolder(binding)
        }

        override fun getItemCount(): Int = dataSet.size

        override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
            holder.render(dataSet[position])
            holder.itemView.setOnClickListener {
                onItemClickListener(holder.adapterPosition)
            }
        }

        fun updateData(dataSet: List<Movies>) {
            this.dataSet = dataSet
            notifyDataSetChanged()
        }


        class MoviesViewHolder(private val binding: ItemMoviesBinding) :
            RecyclerView.ViewHolder(binding.root) {

            //Método para pintar la vista
            fun render(movies: Movies) {
                binding.nameTextView.text = movies.title
                binding.yearTextView.text = movies.year
                Picasso.get().load(movies.imageURL).into(binding.avatarImageView)
            }

        }
    }
