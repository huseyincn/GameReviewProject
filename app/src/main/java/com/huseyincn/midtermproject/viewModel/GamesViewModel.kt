package com.huseyincn.midtermproject.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huseyincn.midtermproject.model.data.Game


class GamesViewModel : ViewModel() {
    private val _gameDatas: MutableLiveData<ArrayList<Game>> = MutableLiveData(ArrayList())
    val liveData: LiveData<ArrayList<Game>> = _gameDatas


    fun addItem(toAdd: Game) {
        _gameDatas.value?.add(toAdd)
    }

    fun updateData(toAdd: ArrayList<Game>) {
        _gameDatas.value?.clear()
        _gameDatas.value?.addAll(toAdd)
    }
}
