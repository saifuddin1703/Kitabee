package com.example.kitabee.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.kitabee.R
import com.example.kitabee.databinding.FragmentHomeBinding
import com.example.kitabee.models.user
import com.example.kitabee.ui.home.adapters.BookAdapter
import com.example.kitabee.ui.home.viewModels.HomeViewModel
import com.example.kitabee.utils.TAG
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val homeViewModel : HomeViewModel by viewModels()
    private lateinit var navController: NavController
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        navController = findNavController()
        binding = FragmentHomeBinding.inflate(inflater)
        homeViewModel.listOfBooks.observe(viewLifecycleOwner,
            {
                binding.bookList.adapter = BookAdapter(it)

            })

        binding.floatingActionButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addBookFragment)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){permissions->

            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }
                else -> {
                    // No location access granted.
                }
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

}