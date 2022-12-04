package com.huseyincn.midtermproject.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.huseyincn.midtermproject.R
import com.huseyincn.midtermproject.model.Game


class AdapterRecycler() : RecyclerView.Adapter<AdapterRecycler.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    private val _data = ArrayList<Game>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: ArrayList<Game>) {
        _data.clear()
        _data.addAll(newList)
        notifyDataSetChanged()
    }

    fun addItem(position: Int, toAdd: Game) {
        _data.add(position, toAdd)
        notifyItemInserted(position)
    }

    fun removeItem(toRemove: Game) {
        val position = _data.indexOf(toRemove)
        if (position != -1) {
            _data.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.gameId)
        val score: TextView = view.findViewById(R.id.scoreId)
        val genre: TextView = view.findViewById(R.id.genres)
        val layout1: RelativeLayout = view.findViewById(R.id.rowlayout)

        init {
            // Define click listener for the ViewHolder's View.
            //val textView : TextView = view.findViewById(R.id.gameId)
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.text_row_item, viewGroup, false)
        return ViewHolder(view, listenerItems)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.text = _data[position].name.toString()
        viewHolder.score.text = _data[position].score.toString()
        viewHolder.genre.text = _data[position].genres.joinToString(", ")
        val color = if (_data[position].isChecked) Color.parseColor("#E0E0E0") else Color.WHITE
        viewHolder.layout1.setBackgroundColor(color)
    }

    private lateinit var listenerItems: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        listenerItems = listener
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = _data.size

}

