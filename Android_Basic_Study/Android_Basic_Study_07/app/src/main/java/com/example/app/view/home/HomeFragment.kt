package com.example.app.view.home

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.data.local.PhotoDao
import com.example.app.data.local.PhotoDaoEntity
import com.example.app.data.local.PhotoDatabase
import com.example.app.databinding.FragmentHomeBinding
import com.example.app.view.DbApplication
import com.example.app.view.MainActivity
import com.example.app.view.detail.DetailFragment
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

        bookmarkRvAdapter = BookmarkRvAdapter().apply {
            this.setItemClickListener(object : OnItemClickListener {
                override fun onItemClick(id: String) {
                    (requireActivity() as MainActivity).replaceFragmentWithBackstack(
                        DetailFragment(id)
                    )
                }
            })
        }
        recentRvAdapter = RecentRvAdapter().apply {
            this.setItemClickListener(object : OnItemClickListener {
                override fun onItemClick(id: String) {
                    (requireActivity() as MainActivity).replaceFragmentWithBackstack(
                        DetailFragment(id)
                    )
                }
            })
        }

        binding.rvBookmark.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvBookmark.adapter = bookmarkRvAdapter


        binding.rvRecentImage.adapter = recentRvAdapter

        observers()
        homeViewModel.getPhotos()
        homeViewModel.getBookmarkPhotos()
//        addSampleData()

        return binding.root
    }

    private fun observers() {
        homeViewModel.photoState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("HomeFragment", "사진 로딩 실패")
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    recentRvAdapter.setData(it.data)
                }
            }
        }
        homeViewModel.bookmarkState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("HomeFragment", "북마크 로딩 실패")
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    bookmarkRvAdapter.setData(it.data)
                }
            }
        }
    }

    private fun addSampleData() {
//        homeViewModel.deleteAllBookmarks()
        // 샘플 데이터 추가
        homeViewModel.addBookmark(
            PhotoDaoEntity(
                "ZjquxEgXheg",
                "https://images.unsplash.com/photo-1718489211836-65a20ad6bd8d?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2MjAyNzh8MHwxfGFsbHx8fHx8fHx8fDE3MTg5NTEzMTV8&ixlib=rb-4.0.3&q=80&w=200"
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}