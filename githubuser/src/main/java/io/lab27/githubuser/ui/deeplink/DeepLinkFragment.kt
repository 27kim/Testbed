package io.lab27.githubuser.ui.deeplink

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import io.lab27.githubuser.R
import io.lab27.githubuser.databinding.FragmentDeepLinkBinding

class DeepLinkFragment : Fragment() {

    lateinit var binding: FragmentDeepLinkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentDeepLinkBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.deepLinkButton.setOnClickListener {
            var args = Bundle().apply {
                putString("arg", "I just came from deep link :)")
            }

            val deepLinkIntent = Navigation.findNavController(binding.root).createDeepLink()
                .setDestination(R.id.aboutFragment)
                .setArguments(args)
                .createPendingIntent()

            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        channelId, "Deep Links", NotificationManager.IMPORTANCE_HIGH
                    )
                )
            }
            val builder = NotificationCompat.Builder(
                requireContext(), channelId
            )
                .setContentTitle("Navigation")
                .setContentText("Deep link to Android")
                .setSmallIcon(R.drawable.ic_hyundai)
                .setContentIntent(deepLinkIntent)
                .setAutoCancel(true)
            notificationManager.notify(0, builder.build())
        }
    }

    companion object {
        const val channelId = "channelSample"
    }
}