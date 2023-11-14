package com.example.myapplication.presentation

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
    }
    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        val c: Calendar = Calendar.getInstance()
        when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..5 -> {
                theme.applyStyle(R.style.Base_Theme_Dark_Blue, true)
            }
            in 6..17 -> {
                theme.applyStyle(R.style.Base_Theme_Blue, true)
            }
            in 18..23 -> {
                theme.applyStyle(R.style.Base_Theme_Dark_Blue, true)
            }

        }
        return theme
    }

}