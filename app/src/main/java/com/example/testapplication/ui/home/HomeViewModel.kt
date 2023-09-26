package com.example.testapplication.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.retrofit.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.testapplication.retrofit.datamodel.Result


class HomeViewModel() : ViewModel() {

    var result = MutableLiveData<Result>()

    private var page = 1

    fun getRepositories(context: Context,date:String){
        val call: Call<Result?>? = RetrofitClient.getRetrofitInstance()?.getApi()?.getRepositories("created:>$date","stars","desc",page)
        call?.enqueue(object: Callback<Result?> {
            override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                val retrofitResult: Result? = response.body() as Result
                val currentRepos = result.value

                this@HomeViewModel.result.postValue(retrofitResult)
                page++
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