package com.example.testapplication.retrofit.view_model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.retrofit.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.testapplication.retrofit.Result


class RepositoryViewModel: ViewModel() {

    var result = MutableLiveData<Result>()

    fun getRepositoriesLastDay(context: Context){
        val call: Call<Result?>? = RetrofitClient.getRetrofitInstance()?.getApi()?.getRepositories("created:>2023-09-20","stars","desc",2)
        call?.enqueue(object: Callback<Result?> {
            override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                val result:Result? = response.body() as Result
                this@RepositoryViewModel.result.postValue(result)
            }

            override fun onFailure(call: Call<Result?>, t: Throwable) {
                Toast.makeText(context, "An error has occurred!", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun getRepositoriesLastWeek(context: Context){
        val call: Call<Result?>? = RetrofitClient.getRetrofitInstance()?.getApi()?.getRepositories("created:>2023-09-16","stars","desc",2)
        call?.enqueue(object: Callback<Result?> {
            override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                val result:Result? = response.body() as Result
                this@RepositoryViewModel.result.postValue(result)
            }

            override fun onFailure(call: Call<Result?>, t: Throwable) {
                Toast.makeText(context, "An error has occurred!", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun getRepositoriesLastMonth(context: Context){
        val call: Call<Result?>? = RetrofitClient.getRetrofitInstance()?.getApi()?.getRepositories("created:>2023-08-01","stars","desc",2)
        call?.enqueue(object: Callback<Result?> {
            override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                val result:Result? = response.body() as Result
                this@RepositoryViewModel.result.postValue(result)
            }

            override fun onFailure(call: Call<Result?>, t: Throwable) {
                Toast.makeText(context, "An error has occurred!", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun observeRepositories(): LiveData<Result>
    {
        return result
    }
}