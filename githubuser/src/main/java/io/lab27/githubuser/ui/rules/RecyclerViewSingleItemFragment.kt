package io.lab27.githubuser.ui.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.lab27.githubuser.databinding.FragmentSingleItemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecyclerViewSingleItemFragment : Fragment() {
    lateinit var binding: FragmentSingleItemBinding
    val viewModel: RulesViewModel by viewModel()

    private val itemAdapter = ItemAdapter { selectedItem -> viewModel.update(selectedItem) }
    private val detailAdapter = DetailAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSingleItemBinding.inflate(
        inflater, container, false
    ).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView1.adapter = itemAdapter
        binding.recyclerView2.adapter = detailAdapter

        binding.radioButton1.setOnClickListener {
            viewModel.changeData(1)
        }

        binding.radioButton2.setOnClickListener {
            viewModel.changeData(2)
        }

        viewModel.items.observe(viewLifecycleOwner) {
            itemAdapter.submitList(it)
        }

        viewModel.details.observe(viewLifecycleOwner) {
            detailAdapter.submitList(it)
        }
    }
}