package com.huseyincn.midtermproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.model.Game
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
        viewModel = ViewModelProvider(this).get(GamesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recView1 = view.findViewById<View>(R.id.recyclerview1) as RecyclerView

        var contacts: ArrayList<Game> = ArrayList(20)
        contacts.add(Game("geyta",23, arrayOf("A","B")))
        contacts.add(Game("valorant",32, arrayOf("c","D")))
        contacts.add(Game("csgo",44, arrayOf("e","F")))

        val adapter = AdapterRecycler(contacts)

        recView1.adapter = adapter

        recView1.layoutManager = LinearLayoutManager(context)
    }
}