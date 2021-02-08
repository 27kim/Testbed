package io.lab27.githubuser.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import io.lab27.githubuser.adapter.*
import io.lab27.githubuser.base.BaseFragment
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.databinding.FragmentMainBinding
import io.lab27.githubuser.util.L
import io.lab27.githubuser.viewmodel.NewsViewModel
import io.lab27.githubuser.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {
    private lateinit var binding: FragmentMainBinding
    private val userViewModel: UserViewModel by sharedViewModel()
    private val newsViewModel: NewsViewModel by viewModel()
    private val remoteAdapter: RemoteAdapter by lazy { RemoteAdapter(viewLifecycleOwner) }
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(viewLifecycleOwner) }
    private val footerAdapter: FooterAdapter by lazy { FooterAdapter(viewLifecycleOwner) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMainBinding.inflate(inflater, container, false).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        setOnClickListener()
    }.root

    private fun setOnClickListener() {
        remoteAdapter.onClick = { user, _ ->
            Log.i("onItemClick", "$user")
            userViewModel.updateUser(user)
        }
        newsAdapter.onClick = { article ->
            val action = MainFragmentDirections.actionWebview(article.url)
            findNavController().navigate(action)
        }
        footerAdapter.onClick = { userViewModel.fetchUserList() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observe()
    }

    private fun initRecyclerView() {
        val newsHeader = HeaderAdapter(viewLifecycleOwner, "News lists")
        val githubHeader = HeaderAdapter(viewLifecycleOwner, "Github user lists")

        ConcatAdapter(ConcatAdapter.Config.Builder().build())
            .apply {
                addAdapter(newsHeader)
                addAdapter(HorizontalContainerAdapter(viewLifecycleOwner, newsAdapter))
                addAdapter(githubHeader)
                addAdapter(remoteAdapter)
                addAdapter(footerAdapter)
            }.also {
                binding.mainRecyclerView.adapter = it
            }
    }


    private fun observe() {
        observeUserList()
        observeLoadingStatus()
        observeError()
        observeFinishState()
        observeNews()
    }

    private fun observeNews() {
        newsViewModel.fetchNews()
        newsViewModel.newsResponse.observe(viewLifecycleOwner, Observer { news ->
            L.i("newsList ? $news")
            newsAdapter.submitList(news)
        })
    }

    private fun observeFinishState() {
        userViewModel.finishState.observe(viewLifecycleOwner, finishStateObserver())
    }

    private fun observeError() {
        userViewModel.error.observe(viewLifecycleOwner, errorObserver())
    }

    private fun observeUserList() {
        userViewModel.fetchUserList()
        userViewModel.userList.observe(viewLifecycleOwner, userListObserver())
    }

    private fun finishStateObserver(): Observer<Boolean> {
        return Observer {
            if (it) requireActivity().finish()
            else Toast.makeText(context, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun errorObserver(): Observer<String> {
        return Observer { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun userListObserver(): Observer<List<User>?> {
        return Observer { result ->
            result?.let {
                if (result.isNotEmpty()) {
                    binding.tvListEmpty.visibility = View.GONE
                    binding.mainRecyclerView.visibility = View.VISIBLE
                    remoteAdapter.submitList(result)
                } else {
                    binding.tvListEmpty.visibility = View.VISIBLE
                    binding.mainRecyclerView.visibility = View.GONE
                }
            }
        }
    }

    private fun observeLoadingStatus() {
        userViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.isLoading = isLoading
        })
    }

    override fun onPause() {
        userViewModel.mediatorLiveData.removeObservers(viewLifecycleOwner)
        super.onPause()
    }

    companion object {
        private const val ARG_POSITION = "position"

        fun getInstance(position: Int) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }
}