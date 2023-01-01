package com.huseyincn.midtermproject.view

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.model.data.Game
import com.huseyincn.midtermproject.model.data.RawgResp
import com.huseyincn.midtermproject.model.repository.rawgInterface
import com.huseyincn.midtermproject.view.adapters.AdapterRecycler
import com.huseyincn.midtermproject.viewModel.DescViewModel
import com.huseyincn.midtermproject.viewModel.GamesViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class Games : Fragment() {

    private lateinit var viewModel: GamesViewModel
    private lateinit var descVM: DescViewModel

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
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recView1 = view.findViewById<View>(R.id.recyclerview1) as RecyclerView
        val searcher: SearchView = view.findViewById(R.id.searcher)
        val nogame: TextView = view.findViewById(R.id.nogamese)
        val adapter = AdapterRecycler()

        nogame.visibility = View.GONE
        recView1.adapter = adapter
        recView1.layoutManager = LinearLayoutManager(context)
        enableBottomNavBar()

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })


        val queryListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.length!! >= 3) {
                    searchTheGame(query,adapter,nogame)
                } else {
                    nogame.visibility = View.GONE
                    viewModel.liveData.value?.let { adapter.updateData(it) }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }

        searcher.setOnQueryTextListener(queryListener)
        setClickListeners(adapter)
    //getDatas(adapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GamesViewModel::class.java]
        descVM = ViewModelProvider(requireActivity())[DescViewModel::class.java]
        getDatasonCreate()
    }

    private fun setClickListeners(adapter: AdapterRecycler) {
        adapter.setOnItemClickListener(object : AdapterRecycler.onItemClickListener {
            override fun onItemClick(position: Int) {
                val item = viewModel.liveData.value!![position]
                item.isChecked = true
                getDesc(viewModel.liveData.value!![position].id)
            }
        })
    }

    private fun filter(models: ArrayList<Game>, query: String): ArrayList<Game>? {
        val lowerCaseQuery = query.lowercase()
        val filteredModelList: ArrayList<Game> = ArrayList()
        for (model in models) {
            val text: String = model.name.lowercase()
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model)
            }
        }
        return filteredModelList
    }

    private fun enableBottomNavBar() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }

    private fun getDatas(adapter: AdapterRecycler) {
        val apiService = retrofit.create(rawgInterface::class.java)
        val results = apiService.getGames()
        results.enqueue(object : Callback<RawgResp> {
            override fun onResponse(call: Call<RawgResp>, response: Response<RawgResp>) {
                if (response.code() == 200) {
                    var gamelist = response.body()
                    gamelist?.let {
                        viewModel.updateData(it.results)
                        adapter.updateData(it.results)
                    }
                    println(gamelist)
                }
            }

            override fun onFailure(call: Call<RawgResp>, t: Throwable) {
                Log.e("hata", "api çekilirken hata oluştu")
            }
        })
    }


    private fun getDatasonCreate() {
        val apiService = retrofit.create(rawgInterface::class.java)
        val results = apiService.getGames()
        results.enqueue(object : Callback<RawgResp> {
            override fun onResponse(call: Call<RawgResp>, response: Response<RawgResp>) {
                if (response.code() == 200) {
                    var gamelist = response.body()
                    gamelist?.let {
                        viewModel.updateData(it.results)
                    }
                    println(gamelist)
                }
            }

            override fun onFailure(call: Call<RawgResp>, t: Throwable) {
                Log.e("hata", "api çekilirken hata oluştu")
            }
        })
    }

    private fun getDesc(id: Int) {
        val apiService = retrofit.create(rawgInterface::class.java)
        val results = apiService.getGameDetail(id)

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
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

    private fun searchTheGame(arancak: String, adapter: AdapterRecycler, nogame: TextView) {
        val apiService = retrofit.create(rawgInterface::class.java)
        val results = apiService.getSearched(searched = arancak)
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here

            val asdasd = results.execute().body()

            if (asdasd != null) {
                if (asdasd.count == 0)
                    nogame.visibility = View.VISIBLE
                else
                    nogame.visibility = View.GONE
                viewModel.updateData(asdasd.results)
                adapter.updateData(asdasd.results)


            }
        }
    }
}
