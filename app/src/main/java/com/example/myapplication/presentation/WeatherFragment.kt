package com.example.myapplication.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentWeatherBinding
import com.example.myapplication.presentation.adapter.WeatherAdapter
import com.example.myapplication.presentation.adapter.`interface`.WeatherAdapterListener
import com.example.myapplication.presentation.view_model.WeatherVM
import com.example.myapplication.util.WeatherIconMapper
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class WeatherFragment : Fragment(), LocationListener {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val viewModel by lazy {
        ViewModelProvider(this)[WeatherVM::class.java]
    }
    private val adapter by lazy {
        WeatherAdapter(object : WeatherAdapterListener {
            override fun clickItem(position: Int, hour: String, degree: Int, image: Int, desc: String) {
               viewModel.selectedItem(position)
                change(hour, degree, image, desc)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        with(binding) {
            vm = viewModel
            lifecycleOwner = this@WeatherFragment
        }
        initAdapter()
        permissionLocation()
        changeBackgroundColor()
        getWeatherOneDay()
        clickTodayButton()
        getEightHourWeather()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        gpsControl()
    }

    private fun permissionLocation() {
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getLocation()
            }
        }
    }

    private fun gpsControl() {
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!gpsEnabled) {

            // Show warning to user if GPS is not enabled
            val dialogBuilder = AlertDialog.Builder(this@WeatherFragment.requireContext())
            dialogBuilder.setMessage(resources.getString(R.string.location_service))
                .setCancelable(false)
                .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                    // Redirect user to GPS settings
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    requireActivity().startActivity(intent)
                }
            val alert = dialogBuilder.create()
            alert.setTitle(resources.getString(R.string.gbs_disabled))
            alert.show()
        } else {
            getLocation()
        }
    }

    private fun clickTodayButton() {
        binding.btToday.setOnClickListener {
            getWeatherOneDay()
            viewModel.selectedItem(-1)
        }
    }


    private fun changeBackgroundColor() {
        val c: Calendar = Calendar.getInstance()
        when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..5 -> {
                binding.linear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_blue
                    )
                )

            }

            in 6..17 -> {
                binding.linear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )

            }

            in 18..23 -> {
                binding.linear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_blue
                    )
                )

            }
        }
    }

    private fun change(hour: String, degree: Int, image: Int, desc: String) {
        if (hour.isEmpty() && degree.toString().isEmpty() && image.toString().isEmpty()) {
            getWeatherOneDay()
        } else {
            with(binding) {
                ivWeather.setImageResource(image)
                "$degree°".let { tvTemp.text = it }
                tvDec.text = desc
            }
        }
    }

    private fun getLocation() {
        // Location permission control
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Request location updates if location permission is granted
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5f, this)

        } else {
            // If location permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // If the user has previously denied permission, show a warning explaining why permission is requested
                val dialogBuilder = AlertDialog.Builder(this@WeatherFragment.requireContext())
                dialogBuilder.setMessage(resources.getString(R.string.device_location))
                    .setCancelable(false)
                    .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                        // Redirect user to application settings
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.parse("package:com.example.myapplication")
                        startActivity(intent)
                    }

                val alert = dialogBuilder.create()
                alert.setTitle(resources.getString(R.string.location_permission))
                alert.show()
            } else {
                // If permission is requested for the first time, request location permission
                requestLocationPermission()
            }
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionCode
        )
    }

    override fun onLocationChanged(location: Location) {
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geoCoder.getFromLocation(location.latitude, location.longitude, 1)

        val cityName = address?.get(0)?.adminArea
        binding.tvCity.text = cityName
        with(viewModel) {
            getWeatherOneDay(location.latitude, location.longitude)
            getEightHourWeather(location.latitude, location.longitude)
        }


    }

    private fun getWeatherOneDay() {
        viewModel.weatherOneDayData.observe(viewLifecycleOwner) { data ->
            try {
                val icon: Int = WeatherIconMapper.getIcon(data?.weather?.get(0)?.icon.toString())
                val temp = data.main.temp
                val tempToDegree = (temp - 273.15).toInt()
                with(binding) {
                    tvDec.text =
                        data?.weather?.get(0)?.description?.replaceFirstChar { it.uppercase() }
                    ivWeather.setImageResource(icon)
                    "$tempToDegree°".let {
                        tvTemp.text = it
                    }
                }
            } catch (e: JsonSyntaxException) {
                errorMessage()
                Log.e("Error", e.toString())
            }
        }
    }

    private fun errorMessage() {
        viewModel.failure.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getEightHourWeather() {
        viewModel.weatherEightHourData.observe(viewLifecycleOwner) { data ->
            try {
                adapter.submitList(data.toList())
                adapter.notifyDataSetChanged()
            } catch (e: JsonSyntaxException) {
                errorMessage()
                Log.e("Error", e.toString())

            }
        }
    }

    private fun initAdapter(){
        with(binding) {
            rv.adapter = adapter
            rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }


}