package com.huseyincn.midtermproject.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huseyincn.midtermproject.model.data.Desc
import com.huseyincn.midtermproject.model.data.Game

class DescViewModel : ViewModel() {

    private val _gameDatas: MutableLiveData<Desc> = MutableLiveData(Desc(1,"","","","",""))
    val liveData: LiveData<Desc> = _gameDatas

    fun setItem(eklencek : Desc) {
        _gameDatas.value = eklencek
    }
}