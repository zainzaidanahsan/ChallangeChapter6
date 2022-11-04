package com.example.challangechapter6.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challangechapter6.R
import com.example.challangechapter6.databinding.FragmentLoginBinding
import com.example.challangechapter6.databinding.FragmentProfileBinding
import com.example.challangechapter6.view.vmView.ProfileviewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class Profile : Fragment() {
    lateinit var binding : FragmentProfileBinding
    private lateinit var viewModel: ProfileviewModel
    private var image_uri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            Log.d("URI_IMG", result.toString())
            binding.myPic.setImageURI(result)
            image_uri = result!!
            viewModel.setImageUri(result)
        }
    private val REQUEST_CODE_PERMISSION = 100

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileviewModel::class.java]
        logout()
        takePict()
        setImageProfileBackground()
        viewModel.getId().observe(viewLifecycleOwner) {
            Log.i("INFOID", it.toString())
            update(it)
            viewModel.getUserById(it)
            setField()
        }
        val image = BitmapFactory.decodeFile(requireActivity().applicationContext.filesDir.path + File.separator +"profiles"+ File.separator +"img-profile.png")
        binding.myPic.setImageBitmap(image)
    }
    private fun takePict() {
        binding.myPic.setOnClickListener {
            checkingPermissions()
        }
    }
    private fun saveImage(){
        val resolver = requireActivity().applicationContext.contentResolver
        val picture = BitmapFactory.decodeStream(
            resolver.openInputStream(Uri.parse(image_uri.toString())))
        saveImageProfile(requireContext(), picture)
        viewModel.applyBlur()
    }
    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,)
        ){
            openGallery()
        }
    }
    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewModel.removeIsLoginStatus()
            viewModel.removeUsername()
            viewModel.removeId()
            viewModel.removeImage()
            viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_profile_to_login)
            }
        }
    }

    private fun setField() {
        viewModel.user.observe(viewLifecycleOwner) { data ->
            binding.apply {
                if (data != null) {
                    etUsername.setText(data.username.toString())
                    etNamaLengkap.setText(data.namaLengkap.toString())
                    etTanggalLahir.setText(data.tanggalLahir.toString())
                    etAlamat.setText(data.alamat.toString())
                }
            }
        }
    }

    private fun setImageProfileBackground() {
        val image =
            BitmapFactory.decodeFile(requireActivity().applicationContext.filesDir.path + File.separator + "blur_outputs" + File.separator + "IMG-BLURRED.png")
        binding.myPic2.setImageBitmap(image)
    }

    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }
    private fun update(id: Int) {
        binding.btnUpdate.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val namaLengkap = binding.etNamaLengkap.text.toString().trim()
            val tanggalLahir = binding.etTanggalLahir.text.toString().trim()
            val alamat = binding.etAlamat.text.toString().trim()
            viewModel.saveImage(image_uri.toString())
            if (image_uri != null) {
                saveImage()
            }
            viewModel.saveUsername(username)
            viewModel.updateUser(id, username, namaLengkap, tanggalLahir, alamat)
            viewModel.applyBlur()
            Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_profile_to_home2)
        }
    }
    private fun saveImageProfile(applicationContext: Context, bitmap: Bitmap): Uri {
        val name = "img-profile.png"
        val outputDir = File(applicationContext.filesDir, "profiles")
        if (!outputDir.exists()) {
            outputDir.mkdirs() // should succeed
        }
        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
        } finally {
            out?.let {
                try {
                    it.close()
                } catch (ignore: IOException) {
                }

            }
        }
        Log.d("URI_IMG", Uri.fromFile(outputFile).toString())
        return Uri.fromFile(outputFile)
    }


}