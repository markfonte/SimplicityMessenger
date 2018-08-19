package fonte.com.simplicitymessenger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(LOG_TAG, context.toString() + '\n' + intent.toString())
        Log.d(LOG_TAG, Telephony.Sms.Intents.getMessagesFromIntent(intent).toString())
    }

    companion object {
        val LOG_TAG: String = SmsReceiver::class.java.simpleName
    }
}
