package com.huseyincn.midtermproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.viewModel.GamesViewModel

class Games : Fragment() {

    companion object {
        fun newInstance() = Games()
    }


    private lateinit var viewModel: GamesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[GamesViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility=View.VISIBLE
        val recView1 = view.findViewById<View>(R.id.recyclerview1) as RecyclerView
        val adapter = AdapterRecycler()
        recView1.adapter = adapter
        recView1.layoutManager = LinearLayoutManager(context)

        viewModel.testArray()

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })

        adapter.setOnItemClickListener(object : AdapterRecycler.onItemClickListener {

            override fun onItemClick(position: Int) {
//                Toast.makeText(
//                    requireActivity(),
//                    "Tıkladığın oyun ismi. ${adapter.gameSpecFromAdapter(position).name.toString()}",
//                    Toast.LENGTH_LONG
//                ).show()

                // OYUNUN DETAIL SAYFASINA GOTURUYOR
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView2, Details()).commit()
            }

        })


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = GamesViewModel()
    }

}