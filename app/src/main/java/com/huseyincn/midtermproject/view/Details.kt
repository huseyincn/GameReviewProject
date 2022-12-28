package com.huseyincn.midtermproject.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.model.data.Game
import com.huseyincn.midtermproject.viewModel.DescViewModel
import com.huseyincn.midtermproject.viewModel.GamesViewModel
import com.squareup.picasso.Picasso

class Details : Fragment() {

    companion object {
        fun newInstance() = Details()
    }

    private lateinit var viewModel: GamesViewModel
    private lateinit var viewModeldesc: DescViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gelenOyun = viewModeldesc.liveData.value

//        val gamePos: Int = bundle1.get("pos") as Int
//        val gameData: Game? = viewModel.liveData.value?.get(gamePos)
        var arananOyun: Game? = null
        val oyunListesi = viewModel.liveData.value
        if (oyunListesi != null) {
            for (game in oyunListesi) {
                if (game.id == viewModeldesc.liveData.value?.id)
                    arananOyun = game
            }
        }
        var isFav = arananOyun?.isFav

        val descr: TextView = view.findViewById(R.id.describ)
        val ressim: ImageView = view.findViewById(R.id.imagegame)
        val reddit: TextView = view.findViewById(R.id.redditlink)
        val websitte: TextView = view.findViewById(R.id.websitelink)
        val oyunismi: TextView = view.findViewById(R.id.gamename)
        val textfav: TextView = view.findViewById(R.id.favourites)

        if (gelenOyun != null) {
            descr.text = gelenOyun.description
            oyunismi.text = gelenOyun.name
            Picasso.get().load(gelenOyun.background_image).fit().into(ressim)
            reddit.setOnClickListener {
                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gelenOyun.reddit_url))
                startActivity(urlIntent)
            }
            websitte.setOnClickListener {
                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gelenOyun.website))
                startActivity(urlIntent)
            }
            textfav.setOnClickListener {
                if (gelenOyun.isFav == false) {
                    gelenOyun.isFav = !gelenOyun.isFav
                    changeTheValue(!gelenOyun.isFav, textfav)
                }
            }
            setClickListeners(view, gelenOyun.isFav)
        }
        disableBottomNav()
    }

    fun disableBottomNav() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.GONE
    }

    fun setClickListeners(view: View, isFav: Boolean?) {
        val textViewback: TextView = view.findViewById(R.id.back2)
        val imgViewBack: ImageView = view.findViewById(R.id.back1)
        val textfav: TextView = view.findViewById(R.id.favourites)


        if (isFav != null) {
            changeTheValue(isFav, textfav)
        }

        textViewback.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, Games()).commit()
        }
        imgViewBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, Games()).commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GamesViewModel::class.java]
        viewModeldesc = ViewModelProvider(requireActivity())[DescViewModel::class.java]

    }

    fun changeTheValue(gameData: Boolean, textFav: TextView) {
        if (gameData) textFav.text = "Favourited"
        else textFav.text = "Favourite"
    }
}

