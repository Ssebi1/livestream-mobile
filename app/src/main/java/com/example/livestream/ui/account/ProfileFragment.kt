package com.example.livestream.ui.account

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.livestream.R
import com.example.livestream.databinding.FragmentProfileBinding
import com.example.livestream.ui.viewmodel.observeOnLifecycle
import com.example.livestream.ui.viewmodel.viewLifecycle
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.File


@AndroidEntryPoint
class ProfileFragment() : Fragment(), View.OnClickListener {

    private val accountViewModel: AccountViewModel by viewModels()
    private var binding: FragmentProfileBinding? by viewLifecycle()
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false).apply {
            viewModel = accountViewModel
            lifecycleOwner = this@ProfileFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeOnLifecycle {
            binding?.apply {
                logoutButton.setOnClickListener(this@ProfileFragment)
                imageView.setOnClickListener(this@ProfileFragment)
            }
            accountViewModel.logoutSuccess.launchAndCollectFlow(this) {
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_login)
            }
        }
        Picasso.get().load("https://leven-tv.com/profile-pictures/" + accountViewModel.loggedUser.id + ".png").into(binding?.imageView);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.logoutButton -> logout()
            R.id.imageView -> take_photo()
        }
    }

    private fun logout() {
        accountViewModel.logout()
    }

    private fun take_photo() {
        val permissionGranted = requestCameraPermission()
        if (permissionGranted) {
            // Open the camera interface
            openCameraInterface()
        }
    }

    private fun requestCameraPermission(): Boolean {
        var permissionGranted = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val cameraPermissionNotGranted = checkSelfPermission(activity as Context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
            if (cameraPermissionNotGranted){
                val permission = arrayOf(Manifest.permission.CAMERA)
                requestPermissions(permission, 1000)
            }
            else{
                permissionGranted = true
            }
        }
        else{
            permissionGranted = true
        }
        return permissionGranted
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1000) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCameraInterface()
            }
            else{
                Toast.makeText(context, "Camera permission was denied. Unable to take a picture.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCameraInterface() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Take picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Take a picture to update your profile")
        imageUri = activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        // Create camera intent
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        // Launch intent
        startActivityForResult(intent, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Callback from camera intent
        if (resultCode == Activity.RESULT_OK){
            // Set image captured to image view
            binding?.imageView?.setImageURI(imageUri)
            val filePath = imageUri?.path ?: return
            val image = File(filePath)

            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", image.toString())
                .build()
            accountViewModel.uploadImage(requestBody)
        }
        else {
            // Failed to take picture
            Toast.makeText(context, "Failed to take picture.", Toast.LENGTH_SHORT).show()
        }
    }
}