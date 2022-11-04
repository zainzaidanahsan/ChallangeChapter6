package com.example.challangechapter6.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challangechapter6.R
import com.example.challangechapter6.databinding.FragmentLoginBinding
import com.example.challangechapter6.datastore.UserDataStoreManager
import com.example.challangechapter6.network.ApiServiceUser
import com.example.challangechapter6.view.vmView.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : Fragment() {
    lateinit var binding : FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =ViewModelProvider(this)[LoginViewModel::class.java]

        binding.textBpa.setOnClickListener {
            it.findNavController().navigate(R.id.action_login_to_register)
        }
        checkUser()

    }
    private fun checkUser() {
        binding.buttonLogin.setOnClickListener {
            viewModel.auth(binding.etUsernameRegister.text.toString(), binding.etPasswordRegister.text.toString())
            viewModel.user.observe(viewLifecycleOwner) {
                if (it != null) {
                    viewModel.saveIsLoginStatus(true)
                    viewModel.saveUsername(it.username.toString())
                    viewModel.saveId(it.id!!.toInt())
                    findNavController().navigate(R.id.action_login_to_home2)
                } else {
                    Toast.makeText(requireContext(), "Login Gagal!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_login_to_home2)
            }
        }
    }

}