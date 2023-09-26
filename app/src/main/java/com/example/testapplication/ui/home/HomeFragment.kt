package com.example.testapplication.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.OnScrollListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.databinding.FragmentHomeBinding
import com.example.testapplication.retrofit.datamodel.Repository
import com.example.testapplication.retrofit.utils.DateUtils
import com.example.testapplication.MainActivity
import com.example.testapplication.room.converter.RepositoryToRepositoryEntityConverter
import com.example.testapplication.ui.sharedviewmodel.SharedViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel : HomeViewModel



    private val viewPager2 by lazy { (requireActivity() as MainActivity).viewPager2 }
    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[SharedViewModel::class.java] }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val dateUtils = DateUtils()

        val lastDay = dateUtils.getLastDay()
        val lastMonth = dateUtils.getLastMonth()
        val lastWeek = dateUtils.getLastWeek()


        val dates = arrayOf(lastMonth,lastWeek,lastDay)



        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.getRepositories(this@HomeFragment.requireContext(), lastDay)


        viewModel.observeRepositories().observe(viewLifecycleOwner, Observer { result->
            val layoutManager = LinearLayoutManager(this@HomeFragment.requireContext(), LinearLayoutManager.VERTICAL,false)
            binding.repoRecycle.layoutManager = layoutManager
            var mylist = result.items as List<*>
            val adapter = RepositoriesAdapter(mylist as List<Repository>, context = this@HomeFragment.requireContext())


            adapter.setOnItemClickListener {
                sharedViewModel.sharedData.postValue(RepositoryToRepositoryEntityConverter.convert(mylist[it]))
                viewPager2.setCurrentItem(0, true)
            }



            binding.repoRecycle.adapter = adapter

        })



        binding.repoRecycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val position = (binding.repoRecycle.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (position + 5 == (binding.repoRecycle.adapter?.itemCount ?: 5)){
                    viewModel.getRepositories(this@HomeFragment.requireContext(),dates[binding.spinner.selectedItemPosition])
                }
            }
        })






        val spinnerData = arrayOf("Last month", "Last week", "Last day")


        val adapter = ArrayAdapter(this@HomeFragment.requireContext(),android.R.layout.simple_spinner_item,spinnerData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                val selectedOption = spinnerData[position]

                when(position){
                    0->{
                        viewModel.getRepositories(this@HomeFragment.requireContext(),lastMonth)
                    }
                    1->{
                        viewModel.getRepositories(this@HomeFragment.requireContext(),lastWeek)
                    }
                    2->{
                        viewModel.getRepositories(this@HomeFragment.requireContext(),lastDay)
                    }
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}