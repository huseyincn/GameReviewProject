package com.huseyincn.midtermproject.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.model.data.Game
import com.huseyincn.midtermproject.model.repository.rawgInterface
import com.huseyincn.midtermproject.view.adapters.AdapterRecycler
import com.huseyincn.midtermproject.viewModel.DescViewModel
import com.huseyincn.midtermproject.viewModel.FavouriteViewModel
import com.huseyincn.midtermproject.viewModel.GamesViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Favourites : Fragment() {

    private lateinit var viewModel: GamesViewModel
    private lateinit var favGamesvm: FavouriteViewModel
    private lateinit var descVM: DescViewModel

    val favGames: ArrayList<Game> = ArrayList()
    val adapter = AdapterRecycler(renkli = false)
    lateinit var headerTitle: TextView

    private val GAMEURL =
        "https://api.rawg.io/api/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(GAMEURL)
        .build()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recview: RecyclerView = view.findViewById(R.id.recyclerview1)
        val nogame: TextView = view.findViewById(R.id.nogame)
        headerTitle = view.findViewById(R.id.header_title1)

        recview.adapter = adapter
        recview.layoutManager = LinearLayoutManager(context)

        setClickListeners(adapter)
        swipeAnimAdd(recview, nogame)

        favGamesvm.liveData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                recview.visibility = View.GONE
                nogame.visibility = View.VISIBLE
            } else {
                recview.visibility = View.VISIBLE
                nogame.visibility = View.GONE
            }
            adapter.updateData(it)
            if (it.count() != 0)
                headerTitle.text = "Favourites (${it.count()})"
            else
                headerTitle.text = "Favourites"
        })

        /*
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            favGames.clear()
            for (game in it) {
                if (game.isFav) favGames.add(game)
            }
            if (favGames.isEmpty()) {
                recview.visibility = View.GONE
                nogame.visibility = View.VISIBLE
            } else {
                recview.visibility = View.VISIBLE
                nogame.visibility = View.GONE
            }
            adapter.updateData(favGames)
            if (favGames.count() != 0)
                headerTitle.text = "Favourites (${favGames.count()})"
            else
                headerTitle.text = "Favourites"
        })
         */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GamesViewModel::class.java]
        favGamesvm = ViewModelProvider(requireActivity())[FavouriteViewModel::class.java]
        descVM = ViewModelProvider(requireActivity())[DescViewModel::class.java]
    }

    private fun setClickListeners(adapter: AdapterRecycler) {
        adapter.setOnItemClickListener(object : AdapterRecycler.onItemClickListener {
            override fun onItemClick(position: Int) {
//                val bundle = Bundle()
//                bundle.putInt("pos", position)
//
//                val fragmentToGo = Details()
//                fragmentToGo.arguments = bundle
//
//                viewModel = ViewModelProvider(requireActivity()).get(GamesViewModel::class.java)
//                val item = viewModel.liveData.value!!.get(position)
//                item.isChecked = true
//                // OYUNUN DETAIL SAYFASINA GOTURUYOR
//                requireActivity().supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainerView2, fragmentToGo).commit()
                getDesc(favGamesvm.liveData.value!![position].id)
            }
        })
    }

    private fun swipeAnimAdd(recview: RecyclerView, nogame: TextView) {
        val itemtouchh = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val builder = AlertDialog.Builder(requireActivity())
                builder.setMessage("Are you sure you want to Delete?").setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->

                        val silincek: Game =
                            favGamesvm.liveData.value?.get(viewHolder.adapterPosition)!!
                        //kodu stackoverflowdan çaldım adam dbden siliyordu ben değeri false yapıyom
//                        val silinecek: Game = favGames.get(viewHolder.adapterPosition)
//                        val setFalse = viewModel.liveData.value?.indexOf(silinecek)
//                        favGames.remove(silinecek)
//                        setFalse?.let { viewModel.liveData.value?.get(it)?.isFav = false }
                        favGamesvm.delData(silincek)
//                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                        adapter.updateData(favGamesvm.liveData.value!!)
                        if (favGamesvm.liveData.value!!.isNotEmpty())
                            headerTitle.text = "Favourites (${favGamesvm.liveData.value!!.count()})"
                        else {
                            headerTitle.text = "Favourites"
                            recview.visibility = View.GONE
                            nogame.visibility = View.VISIBLE
                        }
                    }.setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        adapter.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemtouchh)
        itemTouchHelper.attachToRecyclerView(recview)
    }

    private fun getDesc(id: Int) {
        val apiService = retrofit.create(rawgInterface::class.java)
        val results = apiService.getGameDetail(id)

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here

            val asdasd = results.execute().body()
//            println("aasdasd")
//            val bundle = Bundle()
//            bundle.putStringArrayList(
//                "Desc",
//                arrayListOf(
//                    descVM.liveData.value?.name,
//                    descVM.liveData.value?.description,
//                    descVM.liveData.value?.reddit_url,
//                    descVM.liveData.value?.website,
//                    descVM.liveData.value?.background_image
//                )
//            )
            if (asdasd != null) {
                descVM.setItem(asdasd)
            }
            val fragmentToGo = Details()
//
//         fragmentToGo.arguments = bundle
            // OYUNUN DETAIL SAYFASINA GOTURUYOR
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, fragmentToGo).commit()
        }
    }

}

