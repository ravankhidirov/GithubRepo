package com.example.testapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.testapplication.ui.viewpager.ViewPagerAdapter

class MainActivity : AppCompatActivity() {


    lateinit var viewPager2: ViewPager2
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager2 = binding.pager

        val pagerAdapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        binding.pager.adapter = pagerAdapter
        binding.pager.currentItem = 1

    }
}