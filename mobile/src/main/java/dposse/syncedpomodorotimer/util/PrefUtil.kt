package dposse.syncedpomodorotimer.util

import android.content.Context
import android.preference.PreferenceManager
import dposse.syncedpomodorotimer.TimerActivity

class PrefUtil {

    companion object {

        fun getTimerLength(context: Context): Int{
            //placeholder
            return 1
        }

        private const val PREVIOUS_TIMER_LENGTH_SECONDS_TD = "dposse.syncedpomodorotimer.previous_timer_length"

        fun getPreviousTimerLengthSeconds(context: Context): Long{

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_TD,0)

        }

        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context) {

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_TD,seconds)
            editor.apply()

        }

        private const val TIMER_STATE_ID = "dposse.syncedpomodorotimer.timer_state"

        fun getTimerState(context: Context): TimerActivity.TimerState {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID,0)
            return TimerActivity.TimerState.values()[ordinal]

        }

        fun setTimerState(state: TimerActivity.TimerState, context: Context) {

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID,ordinal)
            editor.apply()

        }

        private const val SECONDS_REMAINING_TD = "dposse.syncedpomodorotimer.seconds_remaining"

        fun getSecondsRemaining(context: Context): Long{

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_TD,0)

        }

        fun setSecondsRemaining(seconds: Long, context: Context) {

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_TD,seconds)
            editor.apply()

        }


    }

}