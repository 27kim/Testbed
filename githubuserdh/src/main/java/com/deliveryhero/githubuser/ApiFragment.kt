package com.deliveryhero.githubuser

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deliveryhero.githubuser.databinding.FragmentRecyclerViewBinding
import com.deliveryhero.githubuser.databinding.RvLayoutUserListBinding
import com.deliveryhero.githubuser.network.User

class ApiFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerViewBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: ApiUserAdapter
    private val userViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }
    private val searchView: SearchView by lazy {
        SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_recycler_view,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        recyclerView = binding.fragmentRecyclerview
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView.clearFocus()
        setupRecyclerView()
        initViewModel()
    }

    private fun setupRecyclerView() {
        showEmptyMsg()

        recyclerView.apply {
            userAdapter = ApiUserAdapter(context, mutableListOf())
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun initViewModel() {
        userViewModel.userData.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                showRecyclerView()
                userAdapter.updateUser(it)
            } else {
                showEmptyMsg()
            }
        })
    }

    private fun showRecyclerView() {
        binding.emptyMsg.visibility = View.GONE
        binding.fragmentRecyclerview.visibility = View.VISIBLE
    }

    private fun showEmptyMsg() {
        binding.emptyMsg.visibility = View.VISIBLE
        binding.fragmentRecyclerview.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater?.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.queryHint = "Search user"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    userViewModel.fetchUserFromApi(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    inner class ApiUserAdapter(
        private val context: Context,
        private var userList: MutableList<User>
    ) : RecyclerView.Adapter<ApiUserAdapter.GithubUserViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {

            val binding = DataBindingUtil.inflate<RvLayoutUserListBinding>(
                LayoutInflater.from(context),
                R.layout.rv_layout_user_list, parent,
                false
            )
            binding.lifecycleOwner = this@ApiFragment
            return GithubUserViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return userList.size
        }

        override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
            holder.onBind(userList[position])
        }

        fun updateUser(list: List<User>) {
            userList.clear()
            userList.addAll(list)
            notifyDataSetChanged()
        }

        inner class GithubUserViewHolder(private var binding: RvLayoutUserListBinding) :
            RecyclerView.ViewHolder(binding.root) {

            init {
                binding.viewModel = userViewModel
            }

            fun onBind(user: User) {
                binding.user = user
                Glide.with(binding.root)
                    .load(user.avatar_url)
                    .placeholder(R.drawable.ic_error)
                    .into(binding.userAvatar)
            }
        }
    }

    companion object {
        fun newInstance(): ApiFragment {
            val args = Bundle()
            val fragment = ApiFragment()
            fragment.arguments = args
            return fragment
        }
    }
}