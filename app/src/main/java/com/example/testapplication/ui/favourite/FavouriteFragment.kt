package com.example.testapplication.ui.favourite

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.testapplication.MainActivity
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentFavouriteBinding
import com.example.testapplication.room.repository.RepositoryEntity
import com.example.testapplication.room.repository.RepositoryRoomDatabase
import com.example.testapplication.ui.sharedviewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavouriteFragment : Fragment() {


    private var _binding: FragmentFavouriteBinding? = null

    private val binding get() = _binding!!

    lateinit var db: RepositoryRoomDatabase

    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[SharedViewModel::class.java] }
    private val favViewModel by lazy { ViewModelProvider(requireActivity())[FavouriteViewModel::class.java] }
    private val viewPager2 by lazy { (requireActivity() as MainActivity).viewPager2 }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val root: View = binding.root


        CoroutineScope(Dispatchers.IO).launch {
            db = Room.databaseBuilder(requireContext(), RepositoryRoomDatabase::class.java, "repository_table")
                .fallbackToDestructiveMigration()
                .build()
            favViewModel.getFaves(db.repositoryDao())
        }


        binding.favRecycle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        favViewModel.observeFaves().observe(viewLifecycleOwner) { favs ->
            val adapter = FavouritesAdapter(requireContext(), favs)

            adapter.setOnItemClickListener {
                sharedViewModel.sharedData.postValue(favs[it])
                viewPager2.setCurrentItem(0, true)
            }

            adapter.setOnRemoveButtonClickListener {
                showCustomAlertDialog(requireContext(),it)
                favViewModel.getFaves(db.repositoryDao())
            }
            binding.favRecycle.adapter = adapter
        }



        binding.refreshLayout.setOnRefreshListener {
            favViewModel.getFaves(db.repositoryDao())
            binding.refreshLayout.isRefreshing = false
        }

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    @SuppressLint("SetTextI18n")
    private fun showCustomAlertDialog(context: Context, repo: RepositoryEntity)
    {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.alert_dialog_layout, null)
        val addFavourite = view.findViewById<AppCompatButton>(R.id.addFavourite)
        val first = view.findViewById<TextView>(R.id.firstText)
        val second = view.findViewById<TextView>(R.id.secondText)
        addFavourite.text = "Remove"
        first.text = "Remove from Favourites!"
        second.text = "After removing, this repository will not be displayed in favourites anymore."
        val alertDialog = AlertDialog.Builder(context,R.style.CustomAlertDialogTheme)
            .setView(view)
            .create()
        addFavourite.setOnClickListener {
            removeFromFavourites(repo)
            favViewModel.getFaves(db.repositoryDao())
            alertDialog.dismiss()
        }
        alertDialog.show()
    }






    private fun removeFromFavourites(repo: RepositoryEntity){
        CoroutineScope(Dispatchers.Default).launch {
            db.repositoryDao().delete(repo)
        }
    }


}