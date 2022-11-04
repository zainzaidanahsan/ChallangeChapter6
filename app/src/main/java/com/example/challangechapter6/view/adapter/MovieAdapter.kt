package com.example.challangechapter6.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challangechapter6.R
import com.example.challangechapter6.databinding.ItemMovieBinding
import com.example.challangechapter6.databinding.ItemMovieBinding.inflate
import com.example.challangechapter6.model.movie.ResultMovie

class MovieAdapter(private val listMovies: List<ResultMovie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        fun bindMovie(dataMovie: ResultMovie) {
            binding.titleMovie.text = dataMovie.title
            binding.releaseMovie.text = "Release at: " + dataMovie.releaseDate
            Glide.with(itemView).load(IMAGE_BASE + dataMovie.posterPath).into(binding.imgMovie)
            binding.cardView.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("ID", dataMovie.id.toString().toInt())
                }
                it.findNavController().navigate(R.id.action_home2_to_detailMovie, bundle)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
    MovieViewHolder(inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        holder.bindMovie(listMovies[position])
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }
}