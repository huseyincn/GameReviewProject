package com.huseyincn.midtermproject.model.repository

import com.huseyincn.midtermproject.model.data.Desc
import com.huseyincn.midtermproject.model.data.RawgResp
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface rawgInterface {

    // API KEYI NORMALDE .env dosyasina koyarim ama bu acik bir api o yuzden sıkıntı yok

    // games? olabilir query gelceginden
    @GET(value = "games")
    fun getGames(
        @Query("key") api_key: String = "3be8af6ebf124ffe81d90f514e59856c",
        @Query("page_size") pageSize: String = "10",
        @Query("page") pageNum: String = "1"
    ): Call<RawgResp> //Call<RawgResp>

    @GET(value = "games")
    fun getSearched(
        @Query("key") api_key: String = "3be8af6ebf124ffe81d90f514e59856c",
        @Query("page_size") pageSize: String = "10",
        @Query("page") pageNum: String = "1",
        @Query("search") searched: String
    ): Call<RawgResp>

    @GET(value = "games/{id}")
    fun getGameDetail(
        @Path("id") id: Int,
        @Query("key") api_key: String = "3be8af6ebf124ffe81d90f514e59856c"
    ): Call<Desc>
}