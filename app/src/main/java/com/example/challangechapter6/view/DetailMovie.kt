package com.example.challangechapter6.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.challangechapter6.R
import com.example.challangechapter6.databinding.FragmentDetailMovieBinding
import com.example.challangechapter6.databinding.FragmentLoginBinding
import com.example.challangechapter6.view.vmView.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovie : Fragment() {
    private lateinit var viewModel: DetailViewModel
    lateinit var binding : FragmentDetailMovieBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        val id = arguments?.getInt("ID")
        if (id != null) {
            viewModel.getMovieById(id)
            observeDetailMovie()
        }
    }

    private fun observeDetailMovie() {
        viewModel.movie.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    titleMovie.text = it.title.toString()
                    releaseDataMovie.text = it.releaseDate.toString()
                    Glide.with(requireContext())
                        .load("https://image.tmdb.org/t/p/w500/" + it.backdropPath)
                        .into(binding.movieImage)
                    descMovie.text = it.overview.toString()
                }
            }
        }
    }
}