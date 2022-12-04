package com.huseyincn.midtermproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.data.repository.MockRepository
import com.huseyincn.midtermproject.viewModel.GamesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GamesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)
        viewModel = ViewModelProvider(this)[GamesViewModel::class.java]
        viewModel.updateData(MockRepository.testArray())
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
