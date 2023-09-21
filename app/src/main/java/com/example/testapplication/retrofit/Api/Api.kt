package com.example.testapplication.retrofit.Api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.testapplication.retrofit.Result
import retrofit2.http.Headers

interface Api {
    @GET("search/repositories")
    @Headers("Accept: application/json")
    fun getRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("page") page: Int
    ): Call<Result?>?
}