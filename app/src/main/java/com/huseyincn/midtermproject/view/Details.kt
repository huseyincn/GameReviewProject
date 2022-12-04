package com.huseyincn.midtermproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.model.Game
import com.huseyincn.midtermproject.viewModel.GamesViewModel

class Details : Fragment() {

    companion object {
        fun newInstance() = Details()
    }

    private lateinit var viewModel: GamesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableBottomNav()
        setClickListeners(view)
    }

    fun disableBottomNav() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.INVISIBLE
    }

    fun setClickListeners(view: View) {
        val textViewback: TextView = view.findViewById(R.id.back2)
        val imgViewBack: ImageView = view.findViewById(R.id.back1)
        val textfav: TextView = view.findViewById(R.id.favourites)
        var bundle1 = this.requireArguments()
        val gamePos: Int = bundle1.get("pos") as Int
        val gameData: Game? = viewModel.liveData.value?.get(gamePos)
        var isFav: Boolean = false
        gameData?.let {
            isFav = it.isFav
        }
        changeTheValue(isFav, textfav)

        textViewback.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, Games()).commit()
        }
        imgViewBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, Games()).commit()
        }
        textfav.setOnClickListener {
            if (!isFav) {
                val prev: Boolean? = viewModel.liveData.value?.get(gamePos)?.isFav
                viewModel.liveData.value?.get(gamePos)?.isFav  = !prev!!
                isFav = !prev
                changeTheValue(!prev,textfav)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GamesViewModel::class.java]
    }

    fun changeTheValue(gameData: Boolean, textFav: TextView) {
        if (gameData) textFav.text = "Favourited"
        else textFav.text = "Favourite"
    }
}
