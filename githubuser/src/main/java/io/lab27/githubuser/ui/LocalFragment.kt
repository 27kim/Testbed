package io.lab27.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.viewmodel.UserViewModel
import io.lab27.githubuser.base.BaseFragment
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.databinding.FragmentMainBinding
import io.lab27.githubuser.databinding.ItemUserBinding
import io.lab27.githubuser.util.L
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LocalFragment : BaseFragment() {
    private val userViewModel: UserViewModel by sharedViewModel()
    private val localAdapter: LocalAdapter by lazy { LocalAdapter() }
    private lateinit var searchView: SearchView
    private lateinit var queryTextListener: SearchView.OnQueryTextListener
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView(inflater)
        return binding.root
    }

    private fun initView(inflater: LayoutInflater) {
        binding = FragmentMainBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        localAdapter.apply {
            onItemClick = { user ->
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
        binding.mainRecyclerView.apply {
            adapter = localAdapter
        }
        observe()
    }

    private fun observe() {
        observeUserListFromDb()
        observeLoadingStatus()
    }

    private fun observeUserListFromDb() {
        userViewModel.localUserList.observe(viewLifecycleOwner, Observer { result ->
            L.i("local list ? $result")
            result
                .filter { it.isFavorite }
                .also { list ->
                    if (result.isNotEmpty()) {
                        localAdapter.submitList(list)
                        binding.tvListEmpty.visibility = View.GONE
                        binding.mainRecyclerView.visibility = View.VISIBLE
                    } else {
                        binding.tvListEmpty.visibility = View.VISIBLE
                        binding.mainRecyclerView.visibility = View.GONE
                    }
                }
        })
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
            searchView?.let {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
                it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            Log.i("onQueryTextSubmit", query)

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
            LocalFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }
}

class LocalAdapter :
    RecyclerView.Adapter<LocalAdapter.LocalViewHolder>() {
    private var items = mutableListOf<User>()
    var onItemClick: ((User) -> Unit)? = null
    lateinit var itemBinding: ItemUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        itemBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LocalViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    fun submitList(items: List<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class LocalViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }

        fun onBind(user: User) {
            binding.user = user

            binding.isStarred.setOnClickListener {
                binding.user?.let { user ->
                    user.isFavorite = !user.isFavorite
                    onItemClick?.invoke(user)
                }
            }
        }
    }
}