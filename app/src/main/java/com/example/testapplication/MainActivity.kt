package com.example.testapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.testapplication.retrofit.Repository
import com.example.testapplication.retrofit.adapter.Adapter
import com.example.testapplication.retrofit.view_model.RepositoryViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel : RepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this)[RepositoryViewModel::class.java]

        viewModel.getRepositoriesLastDay(this)

        viewModel.observeRepositories().observe(this, Observer { result->
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            binding.repoRecycle.layoutManager = layoutManager
            var mylist = result.items as List<*>
            val adapter = Adapter(mylist as List<Repository>, context = this)
            binding.repoRecycle.adapter = adapter

        })



        binding.lastWeek.setOnClickListener {

            viewModel.getRepositoriesLastWeek(this)

        }
        binding.lastMonth.setOnClickListener {
            viewModel.getRepositoriesLastMonth(this)

        }



    }
}