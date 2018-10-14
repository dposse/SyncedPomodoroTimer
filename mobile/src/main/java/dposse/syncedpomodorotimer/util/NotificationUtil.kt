package dposse.syncedpomodorotimer.util

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import dposse.syncedpomodorotimer.AppConstants
import dposse.syncedpomodorotimer.R
import dposse.syncedpomodorotimer.TimerActivity
import dposse.syncedpomodorotimer.TimerNotificationActionReceiver
import java.text.SimpleDateFormat
import java.util.*

class NotificationUtil {

    companion object {

        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "Timer App Timer"
        private const val TIMER_ID = 0

        fun showTimerRunning(context: Context, wakeUpTime: Long) {

            val stopIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            stopIntent.action = AppConstants.ACTION_STOP
            val stopPendingIntent = PendingIntent.getBroadcast(context, 0, stopIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT)

            val pauseIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            pauseIntent.action = AppConstants.ACTION_PAUSE
            val pausePendingIntent = PendingIntent.getBroadcast(context,
                    0,pauseIntent,PendingIntent.FLAG_UPDATE_CURRENT)

            val df = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)


            val notificationBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)

            notificationBuilder.setContentTitle("Timer is running.")
                    .setContentText("End: ${df.format(Date(wakeUpTime))}")
                    .setContentIntent(getPendingIntentWithStack(context, TimerActivity::class.java))
                    .setOngoing(true)
                    .addAction(R.drawable.ic_stop,"Stop",stopPendingIntent)
                    .addAction(R.drawable.ic_pause,"Pause",pausePendingIntent)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER,true)

            notificationManager.notify(TIMER_ID,notificationBuilder.build())


        }//end showTimerExpired

        fun showTimerExpired(context: Context) {

            val startIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            startIntent.action = AppConstants.ACTION_START

            val startPendingIntent = PendingIntent.getBroadcast(context, 0, startIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT)

            val notificationBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)

            notificationBuilder.setContentTitle("Timer Expired!")
                    .setContentText("Start again?")
                    .setContentIntent(getPendingIntentWithStack(context, TimerActivity::class.java))
                    .addAction(R.drawable.ic_play_arrow,"Start",startPendingIntent)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER,true)

            notificationManager.notify(TIMER_ID,notificationBuilder.build())


        }//end showTimerExpired

        fun showTimerPaused(context: Context) {

            val resumeIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            resumeIntent.action = AppConstants.ACTION_START

            val resumePendingIntent = PendingIntent.getBroadcast(context, 0, resumeIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT)

            val notificationBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)

            notificationBuilder.setContentTitle("Timer is paused.")
                    .setContentText("Resume?")
                    .setContentIntent(getPendingIntentWithStack(context, TimerActivity::class.java))
                    .setOngoing(true)
                    .addAction(R.drawable.ic_play_arrow,"Resume",resumePendingIntent)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER,true)

            notificationManager.notify(TIMER_ID,notificationBuilder.build())


        }//end showTimerPaused


        private fun getBasicNotificationBuilder(context: Context, channelId: String,
                                                playSound: Boolean): NotificationCompat.Builder {

            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_timer)
                    .setAutoCancel(true)
                    .setDefaults(0)

            if (playSound) notificationBuilder.setSound(notificationSound)

            return notificationBuilder

        }//end getBasicNotificationBuilder

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>): PendingIntent{

            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        }

        @TargetApi(26)
        private fun NotificationManager.createNotificationChannel(channelId: String,
                                                                  channelName: String,
                                                                  playSound: Boolean){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channelImportance = if (playSound) NotificationManager.IMPORTANCE_DEFAULT
                else NotificationManager.IMPORTANCE_LOW

                val notificationChannel = NotificationChannel(channelId,channelName,channelImportance)

                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.BLUE

                this.createNotificationChannel(notificationChannel)

            }

        }


        fun hideTimerNotification(context: Context) {

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(TIMER_ID)

        }//end hideTimerNotification

    }


}