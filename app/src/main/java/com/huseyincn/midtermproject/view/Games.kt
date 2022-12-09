package com.huseyincn.midtermproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.model.Game
import com.huseyincn.midtermproject.view.adapters.AdapterRecycler
import com.huseyincn.midtermproject.viewModel.GamesViewModel
import kotlin.collections.ArrayList

class Games : Fragment() {

    companion object {
        fun newInstance() = Games()
    }

    private lateinit var viewModel: GamesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
        val recView1 = view.findViewById<View>(R.id.recyclerview1) as RecyclerView
        val searcher : SearchView = view.findViewById(R.id.searcher)
        val adapter = AdapterRecycler()
        recView1.adapter = adapter
        recView1.layoutManager = LinearLayoutManager(context)

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })


        val queryListener =
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.length!! >= 3) {
                        val newArr: ArrayList<Game>? =
                            viewModel.liveData.value?.let {
                                filter(it, newText)
                            }
                        newArr?.let { adapter.updateData(it) }
                        recView1.scrollToPosition(0)
                    } else {
                        viewModel.liveData.value?.let { adapter.updateData(it) }
                    }
                    return true
                }
            }

        searcher.setOnQueryTextListener(queryListener)
        setClickListeners(adapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GamesViewModel::class.java]
    }

    private fun setClickListeners(adapter: AdapterRecycler) {
        adapter.setOnItemClickListener(object : AdapterRecycler.onItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putInt("pos", position)

                val fragmentToGo = Details()
                fragmentToGo.arguments = bundle

                val item = viewModel.liveData.value!![position]
                item.isChecked = true
                // OYUNUN DETAIL SAYFASINA GOTURUYOR
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView2, fragmentToGo).commit()
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

}