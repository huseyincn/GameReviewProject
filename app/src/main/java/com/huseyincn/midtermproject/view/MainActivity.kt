package com.huseyincn.midtermproject.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.model.repository.MockRepository
import com.huseyincn.midtermproject.viewModel.GamesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GamesViewModel
    private lateinit var prefences: SharedPreferences
    val PREFS_FILENAME = "com.huseyincn.prefs"
    val KEY_FAV = "FAVOURITES"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)
        viewModel = ViewModelProvider(this)[GamesViewModel::class.java]
        viewModel.updateData(MockRepository.testArray())
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val favGames: ArrayList<String> = ArrayList()
        prefences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        prefences.getStringSet(KEY_FAV, null)?.let { favGames.addAll(it) }
        if (favGames.count() != 0) {
            val arrL = viewModel.liveData.value
            for (i in favGames) {
                if (arrL != null) {
                    for (x in arrL) {
                        if (x.name.equals(i)) {
                            x.isFav = true
                            break
                        }
                    }
                }
            }
        }
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

    override fun onPause() {
        super.onPause()
        val editor = prefences.edit()
        val tempSet: ArrayList<String> = ArrayList()
        val arrL = viewModel.liveData.value
        if (arrL != null) {
            for (i in arrL) {
                if (i.isFav == true)
                    tempSet.add(i.name)
            }
            editor.putStringSet(KEY_FAV, tempSet.toHashSet())
            editor.commit()
        }
    }
}
