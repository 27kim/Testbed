//package io.lab27.githubuser.util.push
//
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//import com.hyundai.myhyundai.MHNotification.BIG_PICTURE_STYLE
//import com.hyundai.myhyundai.MHNotification.BIG_TEXT_STYLE
//import com.hyundai.myhyundai.MHNotification.NORMAL
//import com.hyundai.myhyundai.R
//import com.hyundai.myhyundai.common.utils.HLog
//import com.hyundai.myhyundai.notifyBigPicturePush
//import com.hyundai.myhyundai.notifyBigTextPush
//import com.hyundai.myhyundai.notifyNormalPush
//
//class MessagingService : FirebaseMessagingService() {
//    override fun onNewToken(s: String) {
//        HLog.d("[push] onNewToken : token=$s")
//    }
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        //todo : need to define message structure!
//        val data = remoteMessage.notification
//        val title = data?.title
//        val text = data?.body
//        val bigText = getString(R.string.lipsum)
//        val imageUrl = data?.imageUrl.toString()
//        val msgType = data?.tag
//
//        HLog.d("[push] onMessageReceived? title: $title text: $text msgType: $msgType imageUrl:$imageUrl")
//
//        if (!title.isNullOrEmpty() && !text.isNullOrEmpty() ) {
//            when (msgType) {
//                NORMAL -> notifyNormalPush(title, text)
//                BIG_PICTURE_STYLE -> notifyBigPicturePush(title, text, imageUrl)
//                BIG_TEXT_STYLE -> notifyBigTextPush(title, text, bigText)
//                else -> notifyBigPicturePush(title, text,  imageUrl)
//            }
//        }
//    }
//}