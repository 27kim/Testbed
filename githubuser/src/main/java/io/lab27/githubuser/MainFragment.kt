package io.lab27.githubuser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.lab27.githubuser.base.BaseDialog
import io.lab27.githubuser.base.BaseFragment
import io.lab27.githubuser.databinding.FragmentFirstBinding
import io.lab27.githubuser.databinding.LayoutRecyclerviewBinding
import io.lab27.githubuser.network.User
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.layout_recyclerview.view.*

class MainFragment : BaseFragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var recyclerViewAdapter: MainAdapter
    private lateinit var searchView: SearchView
    private lateinit var queryTextListener: SearchView.OnQueryTextListener
    private lateinit var binding: FragmentFirstBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner

        }
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = MainAdapter()
        recyclerViewAdapter.apply {
            onItemClick = { user ->
                //DIALOG
                val args = Bundle()
                args.apply {
                    putString("title", user.login)
                    putString("message", user.avatar_url)
                }
                BaseDialog.getInstance(args).show(childFragmentManager, "TEST")
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
    }

    private fun observeUserList() {
        userViewModel.userList.observe(this, Observer { result ->
            run {
                if (result.items.isNotEmpty()) {
                    recyclerViewAdapter.submitList(result.items)
                    tvListEmpty.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                } else {
                    tvListEmpty.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        })
    }

    private fun observeLoadingStatus() {
        userViewModel.isLoading.observe(this, Observer { isLoading ->
            this.isLoading = isLoading
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
                        userViewModel.fetchUserList(query)
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
            MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }
}

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var items = mutableListOf<User>()
    var onItemClick: ((User) -> Unit)? = null
    lateinit var itemBinding: LayoutRecyclerviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        itemBinding = LayoutRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    fun submitList(items: List<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: LayoutRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
            binding.isStarred.setOnClickListener {
                Log.i("isStarred", "isStarred clicked : ${binding.user?.isFavorite}")
            }
        }

        fun onBind(user: User) {
            binding.user = user
            Glide.with(itemView.context).load(user.avatar_url).into(itemView.ivImage)
        }
    }
}