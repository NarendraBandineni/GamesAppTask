package com.example.gamesappnewtask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gamesappnewtask.databinding.FragmentDetailPageBinding
import com.example.gamesappnewtask.utils.DateUtil
import com.example.gamesappnewtask.viewmodels.DetailFragmentViewModel

class DetailPageFragment : Fragment() {
    private lateinit var binding: FragmentDetailPageBinding
    private lateinit var viewModel: DetailFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailPageBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[DetailFragmentViewModel::class.java]
        initUI()
        return binding.root
    }

    private fun initUI() {
        val bundle = arguments
        val _title = bundle?.getString("title")
        val _author = bundle?.getString("author")
        val _content = bundle?.getString("content")
        val _description = bundle?.getString("description")
        val _date = bundle?.getString("date")

        with(viewModel){
            title = _title!!
            author = _author!!
            content = _content!!
            description = _description!!
            date = _date!!
        }

        with(binding){
            title.text = viewModel.title
            content.text = viewModel.content
            description.text = viewModel.description
            author.text = viewModel.author
            date.text = DateUtil.getDateInDDMMYYYY(viewModel.date)
        }

    }

}