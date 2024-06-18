package com.example.app.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.databinding.FragmentHomeBinding
import com.example.app.view.utils.UiState

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookmarkRvAdapter: BookmarkRvAdapter
    private lateinit var recentRvAdapter: RecentRvAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        bookmarkRvAdapter = BookmarkRvAdapter()
        recentRvAdapter = RecentRvAdapter()

        binding.rvBookmark.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvBookmark.adapter = bookmarkRvAdapter


        binding.rvRecentImage.adapter = recentRvAdapter

        observer()
        homeViewModel.getPhotos()

        return binding.root
    }

    private fun observer() {
        homeViewModel.photoState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("TAG1", "사진 로딩 실패")
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    recentRvAdapter.setData(it.data)
                    Log.d("TAG1", "성공")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}