package io.lab27.githubuser.ui

import android.os.Bundle
import android.util.EventLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.Observer
import io.lab27.githubuser.adapter.EventAdapter
import io.lab27.githubuser.base.BaseFragment
import io.lab27.githubuser.databinding.FragmentAuthBinding
import io.lab27.githubuser.databinding.FragmentEventBinding
import io.lab27.githubuser.databinding.FragmentEventBindingImpl
import io.lab27.githubuser.util.L
import io.lab27.githubuser.viewmodel.AuthViewModel
import io.lab27.githubuser.viewmodel.MHViewModel
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.android.synthetic.main.fragment_event.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment : BaseFragment() {
    private lateinit var binding: FragmentEventBinding
    private val viewModel : MHViewModel by viewModel()
    private val adapter : EventAdapter by lazy { EventAdapter(viewLifecycleOwner) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        recyclerview_event.adapter = adapter
    }

    private fun observe() {
        viewModel.fetchEvent()
        viewModel.event.observe(viewLifecycleOwner, Observer { eventList ->
            L.d("eventList? size : ${eventList.size}")
            L.d("eventList? : $eventList")
            adapter.submitList(eventList)
        })
    }


    companion object {
        private const val ARG_POSITION = "position"

        fun getInstance(position: Int) =
            EventFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }
}