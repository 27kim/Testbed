package io.lab27.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import io.lab27.githubuser.adapter.UserLoadStateAdapter
import io.lab27.githubuser.adapter.UserPagingAdapter
import io.lab27.githubuser.base.BaseFragment
import io.lab27.githubuser.databinding.FragmentPagingBinding
import io.lab27.githubuser.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_paging.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PagingFragment : BaseFragment() {
    lateinit var binding: FragmentPagingBinding
    private val pagingAdapter by lazy { UserPagingAdapter() }
    private val userViewModel: UserViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainRecyclerView.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            /**
             * footer/ header
             * */
            UserLoadStateAdapter { pagingAdapter.retry() },
            UserLoadStateAdapter { pagingAdapter.retry() }

        )

        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.listData.collect {
                pagingAdapter.submitData(it)
            }
        }
    }

    companion object {
        private const val ARG_POSITION = "position"

        fun getInstance(position: Int) =
            PagingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }
}