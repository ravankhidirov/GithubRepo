package com.example.testapplication.retrofit.network

import com.example.testapplication.retrofit.api.Api
import com.example.testapplication.retrofit.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object{
        var instance:RetrofitClient? = null
        lateinit var api: Api
        fun getRetrofitInstance():RetrofitClient?{
            if (instance == null){
                instance = RetrofitClient()
                val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                api = retrofit.create<Api>(Api::class.java)
            }

            return instance
        }

    }
    fun getApi():Api?{
        return api
    }
}