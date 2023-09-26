package com.example.testapplication.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testapplication.ui.details.DetailsFragment
import com.example.testapplication.ui.favourite.FavouriteFragment
import com.example.testapplication.ui.home.HomeFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0->DetailsFragment()
            1 -> HomeFragment()
            2 -> FavouriteFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }


}