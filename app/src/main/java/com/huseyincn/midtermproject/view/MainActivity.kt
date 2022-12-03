package com.huseyincn.midtermproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyincn.midtermproject.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)
    }


    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        lateinit var selectedFragment: Fragment
        when (it.itemId) {
            R.id.games -> {
                selectedFragment = Games()
            }
            R.id.favourites -> {
                selectedFragment = Favourites()
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView2, selectedFragment).commit()
        true
    }
}