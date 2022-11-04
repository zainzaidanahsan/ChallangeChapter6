package com.example.challangechapter6.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.challangechapter6.R
import com.example.challangechapter6.databinding.FragmentRegisterBinding
import com.example.challangechapter6.view.vmView.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Register : Fragment() {
    lateinit var binding : FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener(){
            val username = binding.etUsernameRegister.text.toString()
            val email = binding.etEmailRegister.text.toString()
            val password = binding.etPasswordRegister.text.toString()
            val passwordConfirm = binding.etKonfirmPasswordRegister.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                Toast.makeText(requireContext(), "Isi semua jangan sampai kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (password == passwordConfirm) {
                    viewModel.insertUser(username, email, password)
                    Toast.makeText(requireContext(), "Pendaftaran Berhasil", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_register_to_login)
                } else {
                    Toast.makeText(requireContext(), "Password Tidak Cocok", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}