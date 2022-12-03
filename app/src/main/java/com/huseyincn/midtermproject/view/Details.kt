package com.huseyincn.midtermproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.viewModel.DetailsViewModel

class Details : Fragment() {

    companion object {
        fun newInstance() = Details()
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setOnClickListeners(view)
        view.findViewById<TextView>(R.id.back2).setOnClickListener { requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2, Games()).commit()}
        view.findViewById<ImageView>(R.id.back1).setOnClickListener { requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2, Games()).commit()}

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility=View.INVISIBLE
    }
}