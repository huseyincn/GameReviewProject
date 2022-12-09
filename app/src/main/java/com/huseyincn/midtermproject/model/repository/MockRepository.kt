package com.huseyincn.midtermproject.data.repository

import com.huseyincn.midtermproject.model.Game

class MockRepository {

    companion object {
        fun testArray(): ArrayList<Game> {
            val tmpArray: ArrayList<Game> = ArrayList()
            tmpArray.add(Game("valorant", 32, arrayOf("c", "D"), isFav = true))
            tmpArray.add(Game("csgo", 44, arrayOf("e", "F")))
            tmpArray.add(Game("testing", 22, arrayOf("z", "x")))
            tmpArray.add(Game("purposes", 52, arrayOf("sd", "Dasd")))
            tmpArray.add(Game("items", 65, arrayOf("ea", "Fas")))
            tmpArray.add(Game("geyta", 23, arrayOf("A", "B")))
            return tmpArray
        }
    }
}