package com.huseyincn.midtermproject.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huseyincn.midtermproject.model.Game


class GamesViewModel : ViewModel() {
    private val _gameDatas: MutableLiveData<ArrayList<Game>> = MutableLiveData(ArrayList<Game>())
    val liveData : LiveData<ArrayList<Game>> = _gameDatas


    fun addItem(toAdd : Game) {
        _gameDatas.value?.add(toAdd)
    }

    fun removeItem(toRemove : Game){
        val newList = ArrayList<Game>(_gameDatas.value)
        newList.remove(toRemove)
        _gameDatas.value = newList
    }

    fun testArray() {
        addItem(Game("valorant", 32, arrayOf("c", "D")))
        addItem(Game("csgo", 44, arrayOf("e", "F")))
        addItem(Game("testing", 22, arrayOf("z", "x")))
        addItem(Game("purposes", 52, arrayOf("sd", "Dasd")))
        addItem(Game("items", 65, arrayOf("ea", "Fas")))
        addItem(Game("geyta", 23, arrayOf("A", "B")))
    }

}
