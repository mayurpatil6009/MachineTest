package com.example.machinetest

import android.telecom.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface apiInterface {


    @GET("recharge_api/recharge")
    fun getResponce(
        @Query("member_id") memberId: String,
        @Query("api_password") apiPassword: String,
        @Query("api_pin") apiPin: String
    ) : retrofit2.Call<com.example.machinetest.Response>
}