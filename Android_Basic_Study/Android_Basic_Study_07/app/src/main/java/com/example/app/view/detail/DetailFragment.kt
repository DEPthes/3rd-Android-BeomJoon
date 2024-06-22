package com.example.app.view.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.app.databinding.FragmentDetailBinding
import com.example.app.view.MainActivity
import com.example.app.view.utils.UiState

class DetailFragment(private val photoId: String) : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private var isBookmark: Boolean = false
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)

        // NavBar 숨기기
        (activity as MainActivity).hideNavBar()
        detailViewModel.fetchData(photoId)
        detailViewModel.isBookmark(photoId)
        setupListeners()
        setObservers()

        return binding.root
    }

    private fun setObservers() {
        detailViewModel.detailState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("DetailFragment", "상세 정보 로딩 실패")
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    Glide.with(binding.ivPhotoDetail.context)
                        .load(it.data.thumb)
                        .into(binding.ivPhotoDetail)
                    binding.tvPhotoCountry.text = it.data.country
                    binding.tvPhotoCity.text = it.data.city
                    binding.tvPhotoDesc.text = it.data.description
                    binding.tvPhotoLikes.text = it.data.likes.toString()
                    binding.tvPhotoDownloads.text = it.data.downloads.toString()
                    binding.tvPhotoTags.text = it.data.tags.joinToString(" ") { tag -> "#$tag" }

                    setBookmark(it.data.thumb)
                }
            }
        }

        detailViewModel.bookmarkState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("DetailFragment", "북마크 상태 로딩 실패")
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    isBookmark = it.data
                    setBookmarkOpacity(isBookmark)
                }
            }
        }
    }

    private fun setBookmarkOpacity(isBookmark: Boolean) {
        val opacity = if (isBookmark) 0.3f else 1.0f
        binding.ivBookmark.alpha = opacity
    }

    private fun setupListeners() {
        binding.ivExit.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            (activity as MainActivity).showNavBar()
        }
    }

    private fun setBookmark(thumb: String) {
        binding.ivBookmark.setOnClickListener {
            if (isBookmark) {
                detailViewModel.deleteBookmark(photoId)
                Toast.makeText(requireContext(), "북마크에서 삭제 되었습니다", Toast.LENGTH_SHORT).show()
            } else {
                detailViewModel.addBookmark(photoId, thumb)
                Toast.makeText(requireContext(), "북마크에 추가 되었습니다", Toast.LENGTH_SHORT).show()
            }
            setBookmarkOpacity(isBookmark)
            isBookmark = !isBookmark
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}