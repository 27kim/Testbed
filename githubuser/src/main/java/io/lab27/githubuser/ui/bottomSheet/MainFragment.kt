package io.lab27.githubuser.ui.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.lab27.githubuser.databinding.FragmentBottomSheetMainBinding
import io.lab27.githubuser.ui.main.NewsAdapter
import io.lab27.githubuser.util.L
import io.lab27.githubuser.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    lateinit var binding: FragmentBottomSheetMainBinding
    private val newsViewModel: NewsViewModel by viewModel()

    val newsAdapter by lazy {
        NewsAdapter(viewLifecycleOwner)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentBottomSheetMainBinding.inflate(
        inflater, container, false
    ).apply {
        lifecycleOwner = viewLifecycleOwner
        binding = this
        L.i("BottomSheetFragment onCreateView")
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        L.i("BottomSheetFragment onViewCreated")
        binding.recyclerView.apply {
            adapter = newsAdapter
        }
        newsViewModel.fetchNews()
        newsViewModel.newsResponse.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
            L.i("BottomSheetFragment observe ${it.size}")
        }

        BottomSheetDialog().show(parentFragmentManager, "")
    }
}