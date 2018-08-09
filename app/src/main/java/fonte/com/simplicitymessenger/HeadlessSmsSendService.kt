package fonte.com.simplicitymessenger

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class HeadlessSmsSendService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        Log.d(LOG_TAG, intent.toString())
        return null
    }


    companion object {
        val LOG_TAG : String = MmsReceiver::class.java.simpleName
    }
}
