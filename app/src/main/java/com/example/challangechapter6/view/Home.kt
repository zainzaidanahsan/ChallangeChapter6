package com.example.challangechapter6.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challangechapter6.R
import com.example.challangechapter6.databinding.FragmentHomeBinding
import com.example.challangechapter6.view.adapter.MovieAdapter
import com.example.challangechapter6.view.vmView.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {
    private lateinit var viewModel: HomeViewModel
    lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding.imgProfile.setOnClickListener {
            it.findNavController().navigate(R.id.action_home2_to_profile)
        }

        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHome.setHasFixedSize(false)
        viewModel.setMoviesList()
        viewModel.movie.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvHome.adapter = MovieAdapter(it)
            }
        }

        viewModel.getDataStoreUsername().observe(viewLifecycleOwner){
            binding.txtWelcomeUsername.text = "Hi, $it"
        }

    }
}