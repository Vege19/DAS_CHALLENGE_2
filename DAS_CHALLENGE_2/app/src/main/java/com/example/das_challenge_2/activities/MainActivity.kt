package com.example.das_challenge_2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.das_challenge_2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBottomNavigationView()

    }

    private fun setBottomNavigationView() {
        bottomNavigation.setupWithNavController(Navigation.findNavController(this, R.id.navHostFragment))
    }

}
