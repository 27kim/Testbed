package io.lab27.githubuser.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import io.lab27.githubuser.R
import io.lab27.githubuser.adapter.HeaderAdapter
import io.lab27.githubuser.adapter.HorizontalContainerAdapter
import io.lab27.githubuser.databinding.FragmentMoreMainBinding
import io.lab27.githubuser.ui.more.adapter.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoreMainFragment : Fragment() {
    private lateinit var binding: FragmentMoreMainBinding
    private val viewModel: MoreViewModel by sharedViewModel()

    private val menuListAdapter by lazy {
        MenuListAdapter(viewLifecycleOwner)
    }

    private val bottomMenuIconAdapter by lazy {
        AppListAdapter(viewLifecycleOwner, R.layout.layout_app_list)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMoreMainBinding.inflate(inflater, container, false).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ConcatAdapter(ConcatAdapter.Config.Builder().build()).apply {
            addAdapter(UserAdapter(viewLifecycleOwner))
            addAdapter(TopMenuIconAdapter(viewLifecycleOwner))
            addAdapter(menuListAdapter)
            addAdapter(HeaderAdapter(viewLifecycleOwner, "연결 앱"))
            addAdapter(HorizontalContainerAdapter(viewLifecycleOwner, bottomMenuIconAdapter))
        }.also {
            binding.moreRecyclerView.apply {
                adapter = it
            }
        }

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.init()
        viewModel.mainList.observe(viewLifecycleOwner, Observer { list ->
            menuListAdapter.submitList(list)
        })

        viewModel.appList.observe(viewLifecycleOwner, Observer { list ->
            list.map { list -> list.title }.apply {
                bottomMenuIconAdapter.submitList(this)
            }
        })
    }
}

