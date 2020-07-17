package com.oleohialli.cardinfofinder.networking.service

import com.oleohialli.cardinfofinder.models.ResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface CardInfoService {

    @GET
    fun getCardInfo(@Url cardNumber: String): Call<ResponseDTO>

}