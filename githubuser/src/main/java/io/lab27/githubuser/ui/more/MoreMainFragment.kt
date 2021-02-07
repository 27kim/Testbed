package io.lab27.githubuser.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import io.lab27.githubuser.databinding.FragmentMoreMainBinding
import io.lab27.githubuser.ui.more.adapter.MenuAdapter
import io.lab27.githubuser.ui.more.adapter.UserAdapter

class MoreMainFragment : Fragment() {
    private lateinit var binding: FragmentMoreMainBinding

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
            addAdapter(MenuAdapter(viewLifecycleOwner))
        }.also {
            binding.moreRecyclerView.adapter = it
        }
    }
}