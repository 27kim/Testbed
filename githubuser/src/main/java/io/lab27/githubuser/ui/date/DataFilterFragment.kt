package io.lab27.githubuser.ui.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.lab27.githubuser.databinding.FragmentDateFilterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataFilterFragment : Fragment() {
    val viewModel by viewModel<DateFilterViewModel>()
    lateinit var binding: FragmentDateFilterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentDateFilterBinding.inflate(
        inflater, container, false
    ).apply {
        lifecycleOwner = viewLifecycleOwner
        binding = this
        this.vm = viewModel
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.radioMonth1.setOnClickListener {
            viewModel.setStartDate(-1)
        }
        binding.radioMonth3.setOnClickListener {
            viewModel.setStartDate(-3)
        }
        binding.radioMonth6.setOnClickListener {
            viewModel.setStartDate(-6)
        }
    }
}