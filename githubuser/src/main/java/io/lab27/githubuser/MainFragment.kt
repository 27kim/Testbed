package io.lab27.githubuser

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.lab27.githubuser.network.User
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.layout_recyclerview.view.*

class MainFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var recyclerViewAdapter: MainAdapter
//    private lateinit var progressBar: ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerViewAdapter = MainAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = recyclerViewAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * 여기 주석
         * */
//        val argument = requireArguments().getInt(ARG_POSITION)
//        testTv.text = "$argument"

        userViewModel.fetchUserList("27kim")
        userViewModel.userList.observe(this, Observer { result ->
            run {
                recyclerViewAdapter.submitList(result.items)
            }
        })
        userViewModel.isLoading.observe(this, Observer {
            isLoading ->
            run {
                when (isLoading) {
                    true -> progressBar.visibility = View.VISIBLE
                    false -> progressBar.visibility = View.GONE
                }
            }
        })
    }

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(user: User) {
        itemView.tvTitle.text = user.login
        itemView.tvUrl.text = user.html_url
        Glide.with(itemView.context).load(user.avatar_url).into(itemView.ivImage)
    }
}

class MainAdapter :
    RecyclerView.Adapter<MainViewHolder>() {
    private var items: MutableList<User> = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_recyclerview, parent, false)

        return MainViewHolder(view)
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
}