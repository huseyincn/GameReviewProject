package com.huseyincn.midtermproject.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huseyincn.midtermproject.model.data.Game

class FavouriteViewModel : ViewModel() {
    private val _gameDatas: MutableLiveData<ArrayList<Game>> = MutableLiveData(ArrayList())
    val liveData: LiveData<ArrayList<Game>> = _gameDatas


    fun addItem(toAdd: Game) {
        _gameDatas.value?.add(toAdd)
    }

    fun addData(toAdd: List<Game>) {
        _gameDatas.value?.addAll(toAdd)
    }

    fun delData(toDel : Game){
        _gameDatas.value?.remove(toDel)
    }

    fun updateData(toAdd: List<Game>) {
        _gameDatas.value?.clear()
        _gameDatas.value?.addAll(toAdd)
    }
}