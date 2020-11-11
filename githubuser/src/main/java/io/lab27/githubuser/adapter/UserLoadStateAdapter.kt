package io.lab27.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import kotlinx.android.synthetic.main.load_state_view.view.*

class UserLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<UserLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.onBind(loadState, retry)
    }

    class LoadStateViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val progress = view.load_state_progress
        private val btnRetry = view.load_state_retry
        private val txtErrorMessage = view.load_state_errorMessage

        fun onBind(loadState: LoadState, retry: () -> Unit) {
            btnRetry.isVisible = loadState !is LoadState.Loading
            txtErrorMessage.isVisible = loadState !is LoadState.Loading
            progress.isVisible = loadState is LoadState.Loading

            if (loadState is LoadState.Error) {
                txtErrorMessage.text = loadState.error.localizedMessage
            }

            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}
