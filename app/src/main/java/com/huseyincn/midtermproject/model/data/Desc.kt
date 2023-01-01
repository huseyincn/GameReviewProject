package com.huseyincn.midtermproject.model.data

data class Desc(
    val id : Int,
    val name: String,
    val description_raw : String,
    val reddit_url : String,
    val website : String,
    val background_image : String,
    var isFav : Boolean = false
)
