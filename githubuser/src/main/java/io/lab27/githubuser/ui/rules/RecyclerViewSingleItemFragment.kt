package io.lab27.githubuser.ui.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.lab27.githubuser.databinding.FragmentRulesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecyclerViewSingleItemFragment : Fragment(){
    lateinit var binding: FragmentRulesBinding
    val viewModel: RulesViewModel by viewModel()

    private val itemAdapter = ItemAdapter()
    private val detailAdapter = DetailAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentRulesBinding.inflate(
        inflater, container, false
    ).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView1.adapter = itemAdapter.apply {
            clickListener = {
                   viewModel.update(it)
            }
        }
        binding.recyclerView2.adapter = detailAdapter

        binding.radioButton1.setOnClickListener {
            viewModel.changeData()
        }

        binding.radioButton2.setOnClickListener {
            viewModel.changeData()
        }

        viewModel.items.observe(viewLifecycleOwner) {
            itemAdapter.submitList(it)
        }

        viewModel.details.observe(viewLifecycleOwner) {
            detailAdapter.submitList(it)
        }
    }
}