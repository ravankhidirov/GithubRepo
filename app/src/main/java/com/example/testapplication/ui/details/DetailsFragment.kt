package com.example.testapplication.ui.details

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentDetailsBinding
import com.example.testapplication.room.repository.RepositoryEntity
import com.example.testapplication.room.repository.RepositoryRoomDatabase
import com.example.testapplication.ui.favourite.FavouriteViewModel
import com.example.testapplication.ui.sharedviewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[SharedViewModel::class.java] }
    private val favsViewModel by lazy { ViewModelProvider(requireActivity())[FavouriteViewModel::class.java] }

    private lateinit var db:RepositoryRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        CoroutineScope(Dispatchers.IO).launch {
            db = Room.databaseBuilder(requireContext(), RepositoryRoomDatabase::class.java, "repository_table")
                .fallbackToDestructiveMigration()
                .build()
        }


        sharedViewModel.sharedData.observe(viewLifecycleOwner, Observer {
            binding.name.text = it.name
            binding.language.text = it.language
            val repo = it
            Glide.with(this.requireContext())
                .load(it.avatar_url)
                .into(binding.profileImage)

            binding.fab.setOnClickListener {
                showCustomAlertDialog(this@DetailsFragment.requireContext(),repo)
            }

            binding.watch.text = it.watch.toString()
            binding.description.text = it.description
            binding.forks.text = it.forks.toString()
            binding.htmlUrl.text = it.html_url
        })

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showCustomAlertDialog(context: Context,repo: RepositoryEntity)
    {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.alert_dialog_layout, null)
        val addFavourite = view.findViewById<AppCompatButton>(R.id.addFavourite)
        val alertDialog = AlertDialog.Builder(context,R.style.CustomAlertDialogTheme)
            .setView(view)
            .create()
        addFavourite.setOnClickListener {
            addToFavourites(repo)
            favsViewModel.getFaves(db.repositoryDao())
            alertDialog.dismiss()
        }
        alertDialog.show()
    }


    private fun addToFavourites(repo:RepositoryEntity){
        CoroutineScope(Dispatchers.Default).launch {
            db.repositoryDao().addFav(repo)
        }
    }


}