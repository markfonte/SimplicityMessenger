package fonte.com.simplicitymessenger

import android.arch.lifecycle.MutableLiveData
import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony

class SMSQueryRepository {

    private var mQueryHandler: QueryHandler? = null

    fun fetchData(contentResolver: ContentResolver): MutableLiveData<*> {
        mQueryHandler = QueryHandler(contentResolver)
        return query(TOKEN_LOAD_INBOX, Telephony.Sms.Conversations.CONTENT_URI, null, null, null, Telephony.Sms.Conversations.DEFAULT_SORT_ORDER)
    }

    fun query(token: Int, uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, orderBy: String?): MutableLiveData<String> {
        val result: MutableLiveData<String> = MutableLiveData()
        mQueryHandler?.startQuery(token, result, uri, projection, selection, selectionArgs, orderBy)
        return result
    }

    companion object {
        @Volatile
        private var instance: SMSQueryRepository? = null

        private val TOKEN_LOAD_INBOX = 0

        fun getInstance() {
            instance ?: SMSQueryRepository()
        }

        private class QueryHandler(cr: ContentResolver?) : AsyncQueryHandler(cr) {
            override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                val mutableData: MutableLiveData<*> = cookie as MutableLiveData<*>
                try {
                    when (token) {
                        TOKEN_LOAD_INBOX -> if (cursor?.moveToNext()!!) {
                            cookie.value = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Conversations.THREAD_ID))
                        }
                    }
                } finally {
                    cursor?.close()
                }
            }
        }
    }
}