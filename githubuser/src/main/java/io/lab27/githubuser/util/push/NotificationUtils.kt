package io.lab27.githubuser.util.push

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.lab27.githubuser.R
import io.lab27.githubuser.ui.MainActivity
import io.lab27.githubuser.util.L


object MHNotification {
    const val NOTIFICATION = "NOTIFICATION"

    const val BIG_PICTURE_STYLE = "BIG_PICTURE_STYLE"
    const val BIG_TEXT_STYLE = "BIG_TEXT_STYLE"
    const val NORMAL = "NORMAL"

    /**
     * init Notification Channel
     * */
    internal fun initChannel(application: Application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            application.getNotificationManager()?.run {
                val notification = NotificationChannel(
                    application.getString(R.string.notification_channel_id),
                    application.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
                createNotificationChannel(notification)
            }
        }
    }

    inline fun notifyPush(ctx: Context, intercept: Builder.() -> Builder) {
        ctx.getNotificationManager()?.run {
            notify(1, Builder(ctx, MHNotification.NOTIFICATION).intercept().build())
        }
    }

    fun createLauncherIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
    }
}


//todo : where to move extensions below?
/**
 * Builds and delivers the notification.
 */
fun Context.getNotificationManager(): NotificationManagerCompat? {
    return NotificationManagerCompat.from(applicationContext)
}

/**
 * show notification message
 * */
fun Context.notifyNormalPush(title: String, text: String) {
    val intent = MHNotification.createLauncherIntent(this)

    MHNotification.notifyPush(this) {
        setSmallIcon(R.drawable.ic_hyundai)
        setContentTitle(title)
        setContentText(text)
        setAutoCancel(true)
        setContentIntent(intent)
    }
}

fun Context.notifyBigTextPush(title: String, text: String, bigText: String) {
    val intent = MHNotification.createLauncherIntent(this)

    MHNotification.notifyPush(this) {
        setSmallIcon(R.drawable.ic_hyundai)
        setContentTitle(title)
        setContentText(text)
        setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
        setAutoCancel(true)
        setContentIntent(intent)
    }
}


fun Context.notifyBigPicturePush(title: String, text: String, url: String) {
    val intent = MHNotification.createLauncherIntent(this)

    Glide.with(applicationContext)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>(200, 200) {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                L.i("[push] notifyBigStylePush onResourceReady")
                MHNotification.notifyPush(applicationContext) {
                    setSmallIcon(R.drawable.ic_hyundai)
                    setContentTitle(title)
                    setContentText(text)
                    setAutoCancel(true)
                    setContentIntent(intent)
                    setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                }
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                L.e("[push] notifyBigStylePush onLoadFailed")
                MHNotification.notifyPush(applicationContext) {
                    setSmallIcon(R.drawable.ic_hyundai)
                    setContentTitle(title)
                    setContentText(text)
                    setAutoCancel(true)
                    setContentIntent(intent)
                }
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                L.d("[push] notifyBigStylePush onLoadCleared")
            }
        })
}