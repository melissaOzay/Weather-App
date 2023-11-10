package com.example.myapplication.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
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
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
    private val viewModel by lazy {
        ViewModelProvider(this)[WeatherVM::class.java]
    }
    private val adapter by lazy {
        WeatherAdapter(object : WeatherAdapterListener {
            override fun clickItem(hour: String, degree: Int, image: Int) {
                change(hour, degree, image)
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
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        changeBackgroundColor()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!gpsEnabled) {
            val dialogBuilder = AlertDialog.Builder(this@WeatherFragment.requireContext())
            dialogBuilder.setMessage(resources.getString(R.string.location_service))
                .setCancelable(false)
                .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
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


    private fun changeBackgroundColor() {
        val c: Calendar = Calendar.getInstance()
        when (c.get(Calendar.HOUR_OF_DAY)) {
            in 1..5 -> {
                binding.linear.setBackgroundColor(Color.parseColor("#1D2837"))
            }

            in 6..17 -> {
                binding.linear.setBackgroundColor(Color.parseColor("#BCE8FF"))

            }

            in 18..24 -> {
                binding.linear.setBackgroundColor(Color.parseColor("#1D2837"))
            }
        }
    }

    private fun change(hour: String, degree: Int, image: Int) {
        if (hour.isEmpty() && degree.toString().isEmpty() && image.toString().isEmpty()) {
            getWeatherOneDay()
        } else {
            with(binding) {
                ivWeather.setImageResource(image)
                tvTemp.text = "$degree°"
            }
        }
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5f, this)


        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                val dialogBuilder = AlertDialog.Builder(this@WeatherFragment.requireContext())
                dialogBuilder.setMessage(resources.getString(R.string.device_location))
                    .setCancelable(false)
                    .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.parse("package:com.example.myapplication")
                        startActivity(intent)
                    }

                val alert = dialogBuilder.create()
                alert.setTitle(resources.getString(R.string.location_permission))
                alert.show()
            } else {
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
        val address = geoCoder.getFromLocation(location.latitude, location.longitude, 3)

        val cityName = address?.get(0)?.adminArea
        binding.tvCity.text = cityName
        with(viewModel) {
            getWeatherOneDay(location.latitude, location.longitude)
            getEightHourWeather(location.latitude, location.longitude)
        }
        getWeatherOneDay()
        getEightHourWeather()

    }

    private fun getWeatherOneDay() {
        viewModel.weatherOneDayData.observe(viewLifecycleOwner, Observer { data ->
            try {
                val icon: Int = WeatherIconMapper.getIcon(data?.weather?.get(0)?.icon.toString())
                val temp = data.main.temp
                val tempToDegree = (temp - 273.15).toInt()
                with(binding) {
                    tvDec.text =
                        data?.weather?.get(0)?.description?.replaceFirstChar { it.uppercase() }
                    ivWeather.setImageResource(icon)
                    tvTemp.text = "$tempToDegree°"
                }
            } catch (e: JsonSyntaxException) {
                errorMessage()
                Log.e("Error", e.toString())
            }
        })
    }

    private fun errorMessage() {
        viewModel.failure.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    //eight hour weather
    private fun getEightHourWeather() {
        viewModel.weatherEightHourData.observe(viewLifecycleOwner, Observer { data ->
            try {
                with(binding) {
                    rv.adapter = adapter
                    rv.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }
                adapter.setListData(data.list)

            } catch (e: JsonSyntaxException) {
                errorMessage()
                Log.e("Error", e.toString())

            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }

        }
    }

}