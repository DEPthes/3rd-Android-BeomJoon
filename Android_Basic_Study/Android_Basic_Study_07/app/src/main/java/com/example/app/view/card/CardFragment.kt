package com.example.app.view.card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.app.databinding.FragmentCardBinding
import com.example.app.view.detail.DetailViewModel
import com.example.app.view.utils.UiState
import kotlin.math.abs

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val cardViewModel: CardViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        cardViewModel.getRandomPhotos()

        // ViewPage Adapter 초기화
        viewPagerAdapter = ViewPagerAdapter(detailViewModel).apply {
            onBookmarkClicked = { position ->
                // 북마크 클릭 시 다음 아이템으로 이동
                val nextItem = position + 1
                if (nextItem < itemCount) {
                    binding.viewPagerCard.setCurrentItem(nextItem, true)
                }
            }
        }

        binding.viewPagerCard.offscreenPageLimit = 3
        binding.viewPagerCard.setPadding(10, 0, 10, 0)
        binding.viewPagerCard.adapter = viewPagerAdapter
        binding.viewPagerCard.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(20))

        transform.addTransformer(ViewPager2.PageTransformer { view: View, fl: Float ->
            val v = 1 - abs(fl)
            view.scaleY = 0.8f + v * 0.2f
        })

        binding.viewPagerCard.setPageTransformer(transform)
    }

    private fun observer() {
        cardViewModel.randomState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("TAG2", "랜덤 사진 로딩 실패")
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    viewPagerAdapter.setData(it.data)
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
