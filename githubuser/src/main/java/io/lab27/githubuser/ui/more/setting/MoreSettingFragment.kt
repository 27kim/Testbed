package io.lab27.githubuser.ui.more.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.lab27.githubuser.R
import io.lab27.githubuser.databinding.LayoutMoreSettingBinding
import io.lab27.githubuser.ui.more.MoreViewModel
import io.lab27.githubuser.ui.more.adapter.MoreSettingAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoreSettingFragment : Fragment(R.layout.layout_more_setting) {
    private lateinit var binding: LayoutMoreSettingBinding
    private val viewModel: MoreViewModel by sharedViewModel()
    private val moreSettingAdapter by lazy {
        MoreSettingAdapter(viewLifecycleOwner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LayoutMoreSettingBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        binding.moreSettingRecyclerview.adapter = moreSettingAdapter
        viewModel.settingList.observe(viewLifecycleOwner, Observer { list ->
            moreSettingAdapter.submitList(list)
        })
    }
}