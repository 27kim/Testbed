package io.lab27.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import io.lab27.githubuser.R
import io.lab27.githubuser.adapter.*
import io.lab27.githubuser.base.BaseFragment
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.databinding.FragmentRemoteBinding
import io.lab27.githubuser.util.L
import io.lab27.githubuser.viewmodel.NewsViewModel
import io.lab27.githubuser.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_remote.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RemoteFragment : BaseFragment() {
    val userViewModel: UserViewModel by sharedViewModel()
    private val newsViewModel: NewsViewModel by viewModel()
    private val remoteAdapter: RemoteAdapter by lazy { RemoteAdapter(viewLifecycleOwner) }
    private val bannerAdapter: BannerAdapter by lazy { BannerAdapter(viewLifecycleOwner) }
    private lateinit var searchView: SearchView
    private lateinit var queryTextListener: SearchView.OnQueryTextListener
    private lateinit var binding: FragmentRemoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView(inflater)
        return binding.root
    }

    private fun initView(inflater: LayoutInflater) {
        binding = FragmentRemoteBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        remoteAdapter.apply {
            onItemClick = { user, position ->
                Log.i("onItemClick", "$user")
                userViewModel.updateUser(user)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observe()
    }

    private fun initRecyclerView() {
        val newsHeader = HeaderAdapter(viewLifecycleOwner, "News lists")
        val newsAdapter = HorizontalContainerAdapter(viewLifecycleOwner, bannerAdapter)
        bannerAdapter.action = {article ->
            val action  = RemoteFragmentDirections.actionWebview(article.url)
            findNavController().navigate(action)
        }
        val githubHeader = HeaderAdapter(viewLifecycleOwner, "Github user lists")
        val githubFooter = FooterAdapter(this, "Add Article") {
            userViewModel.fetchUserList()
        }

        val concatAdapter = ConcatAdapter(
            ConcatAdapter.Config.Builder().build(),
            newsHeader,
            newsAdapter,
            githubHeader,
            remoteAdapter,
            githubFooter
        )

        recyclerView.apply {
            adapter = concatAdapter
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
            bannerAdapter.submitList(news)
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
                    tvListEmpty.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    remoteAdapter.submitList(result)
                } else {
                    tvListEmpty.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchItem?.let {
            searchView = searchItem.actionView as SearchView
        }

        searchView?.let {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        Log.i("onQueryTextSubmit", query)
                        userViewModel.setQuery(query)
                    }
                    it.onActionViewCollapsed()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        Log.i("onQueryTextChange", newText)
                    }
                    return false
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSearch -> return false
        }
        searchView.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ARG_POSITION = "position"

        fun getInstance(position: Int) =
            RemoteFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }
}