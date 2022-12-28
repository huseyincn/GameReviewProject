package com.huseyincn.midtermproject.model.data

data class RawgResp(
    val count : Int,
    val next : String,
    val previous: String?,
    val results: List<Game>
)