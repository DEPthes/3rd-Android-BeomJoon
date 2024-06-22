package com.example.app.view.detail

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.FragmentDetailBinding
import com.example.app.view.MainActivity
import com.example.app.view.home.HomeViewModel
import com.example.app.view.utils.UiState

class FullFragment(private val photoId: String) : DialogFragment() {
    private var _binding: FragmentDetailBinding? = null
    private var isBookmark: Boolean = false
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // false로 설정해 주면 화면밖 혹은 뒤로가기 버튼 시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.dialog_fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

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
            // NavBar 표시
            (activity as MainActivity).hideNavBar()
            homeViewModel.getBookmarkPhotos()

            // Remove the fragment
            dismiss()
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