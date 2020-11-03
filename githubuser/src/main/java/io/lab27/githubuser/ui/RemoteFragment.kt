package io.lab27.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.lab27.githubuser.R
import io.lab27.githubuser.adapter.MainAdapter
import io.lab27.githubuser.viewmodel.UserViewModel
import io.lab27.githubuser.base.BaseFragment
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.databinding.FragmentRemoteBinding
import kotlinx.android.synthetic.main.fragment_remote.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RemoteFragment : BaseFragment() {
    val userViewModel: UserViewModel by sharedViewModel()
    private lateinit var recyclerViewAdapter: MainAdapter
    private lateinit var searchView: SearchView
    private lateinit var queryTextListener: SearchView.OnQueryTextListener
    private lateinit var binding: FragmentRemoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FragmentRemoteBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initRecyclerView()
        return binding.root
    }


    private fun initRecyclerView() {
        recyclerViewAdapter = MainAdapter()
        recyclerViewAdapter.apply {
            onItemClick = { user, position ->
                Log.i("onItemClick", "$user")
                userViewModel.updateUser(user)
                this.updateItem(position)
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = recyclerViewAdapter
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {
        observeUserList()
        observeLoadingStatus()
        observeError()
        observeFinishState()
    }

    private fun observeFinishState() {
        userViewModel.finishState.observe(viewLifecycleOwner, finishStateObserver())
    }

    private fun observeError() {
        userViewModel.error.observe(viewLifecycleOwner, errorObserver())
    }

    private fun observeUserList() {
        userViewModel.testUser.observe(viewLifecycleOwner, userListObserver())
//        userViewModel.mediatorLiveData.observe(viewLifecycleOwner, userListObserver())
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

    private fun userListObserver(): Observer<List<User>> {
        return Observer { result ->
            run {
                if (result.isNotEmpty()) {
                    recyclerViewAdapter.submitList(result)
                    tvListEmpty.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                } else {
                    tvListEmpty.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        }
    }

    override fun onPause() {
        userViewModel.mediatorLiveData.removeObservers(viewLifecycleOwner)
        super.onPause()
    }

    private fun observeLoadingStatus() {
        userViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.isLoading = isLoading
        })
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
                        userViewModel.getUserList(query)
//                        userViewModel.fetch(query)
                    }
                    it.onActionViewCollapsed()
//                    it.clearFocus()
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