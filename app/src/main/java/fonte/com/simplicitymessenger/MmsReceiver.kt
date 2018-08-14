package fonte.com.simplicitymessenger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(LOG_TAG, context.toString() + '\n' + intent.toString())
    }

    companion object {
        val LOG_TAG: String = MmsReceiver::class.java.simpleName
    }
}
