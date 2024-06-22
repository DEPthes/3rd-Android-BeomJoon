package com.example.app.view.card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.databinding.FragmentCardBinding
import com.example.app.view.detail.DetailViewModel
import com.example.app.view.utils.UiState

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!
    private lateinit var cardRvAdapter: CardRvAdapter
    private val cardViewModel: CardViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(layoutInflater)
        cardRvAdapter = CardRvAdapter(requireContext(), detailViewModel)

        binding.rvRandomPhotos.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvRandomPhotos.adapter = cardRvAdapter

        observer()
        cardViewModel.getRandomPhotos()

        return binding.root
    }

    private fun observer() {
        cardViewModel.randomState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("TAG2", "랜덤 사진 로딩 실패")
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    cardRvAdapter.setData(it.data)
                    Log.d("TAG2", "랜덤 사진 로딩 성공")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}