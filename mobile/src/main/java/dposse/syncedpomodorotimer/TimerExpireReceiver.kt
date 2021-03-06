package dposse.syncedpomodorotimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dposse.syncedpomodorotimer.util.NotificationUtil
import dposse.syncedpomodorotimer.util.PrefUtil

class TimerExpireReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(TimerActivity.TimerState.Stopped,context)
        PrefUtil.setAlarmSetTime(0,context)

    }
}
