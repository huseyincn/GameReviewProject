package com.huseyincn.midtermproject.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.model.Game
import com.huseyincn.midtermproject.view.adapters.AdapterRecycler
import com.huseyincn.midtermproject.viewModel.GamesViewModel

class Favourites : Fragment() {

    private lateinit var viewModel: GamesViewModel
    val favGames: ArrayList<Game> = ArrayList()
    val adapter = AdapterRecycler(renkli = false)

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
        val recylay : ConstraintLayout = view.findViewById(R.id.recviewlay)
        val linlay : LinearLayout = view.findViewById(R.id.linlay)

        recview.adapter = adapter
        recview.layoutManager = LinearLayoutManager(context)

        setClickListeners(adapter)
        swipeAnimAdd(recview)

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            favGames.clear()
            for (game in it) {
                if (game.isFav) favGames.add(game)
            }
            if (favGames.isEmpty()) {
//                recview.visibility = View.GONE
                recylay.visibility = View.GONE
//                nogame.visibility = View.VISIBLE
                linlay.visibility = View.VISIBLE
            } else {
//                recview.visibility = View.VISIBLE
                recylay.visibility = View.VISIBLE
//                nogame.visibility = View.GONE
                linlay.visibility = View.GONE
            }
            adapter.updateData(favGames)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GamesViewModel::class.java]
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
            }
        })
    }

    private fun swipeAnimAdd(recview: RecyclerView) {
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

                        //kodu stackoverflowdan çaldım adam dbden siliyordu ben değeri false yapıyom
                        val silinecek: Game = favGames.get(viewHolder.adapterPosition)
                        val setFalse = viewModel.liveData.value?.indexOf(silinecek)
                        favGames.remove(silinecek)
                        setFalse?.let { viewModel.liveData.value?.get(it)?.isFav = false }
//                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                        adapter.updateData(favGames)
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
}

