//package io.lab27.githubuser.ui.more
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.ui.platform.ComposeView
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.ConcatAdapter
//import io.lab27.githubuser.R
//import io.lab27.githubuser.adapter.HeaderAdapter
//import io.lab27.githubuser.adapter.HorizontalContainerAdapter
//import io.lab27.githubuser.databinding.FragmentMoreMainBinding
//import io.lab27.githubuser.ui.more.adapter.*
//import org.koin.androidx.viewmodel.ext.android.sharedViewModel
//
//class MorePassWordFragment : Fragment() {
//    private lateinit var binding: FragmentMoreMainBinding
//    private val viewModel: MoreViewModel by sharedViewModel()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View = ComposeView(requireContext()).apply {
//        setContent {
//            MaterialTheme {
//                Text("Hello Compose!")
//            }
//        }
//    }
//
//}
//
