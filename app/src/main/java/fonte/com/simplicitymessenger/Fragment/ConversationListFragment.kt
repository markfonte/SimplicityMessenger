package fonte.com.simplicitymessenger.Fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.Telephony
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import fonte.com.simplicitymessenger.R


class ConversationListFragment : Fragment() {
    private var conversationListView: ListView? = null

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView: View = inflater.inflate(R.layout.conversation_list_fragment, null)
        conversationListView = rootView.findViewById(R.id.conversation_list)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val permissionCheck: Int = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_SMS)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            loadConversationList()
        } else {
            context?.resources?.getInteger(R.integer.request_code_permission_read_sms)?.let { ActivityCompat.requestPermissions(activity as Activity, arrayOf(android.Manifest.permission.READ_SMS), it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == context?.resources?.getInteger(R.integer.request_code_permission_read_sms)) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadConversationList()
            } else {
                Log.e(LOG_TAG, "Cannot be displayed without required permissions")
            }
        }
    }

    private fun loadConversationList() {
        val listItems: ArrayList<String?> = ArrayList(0)
        val contentResolver: ContentResolver? = activity?.contentResolver
        //val cursor: Cursor? = contentResolver?.query(Telephony.Sms.CONTENT_URI, arrayOf("*"), null, null, Telephony.Sms.DEFAULT_SORT_ORDER)
        val cursor: Cursor? = contentResolver?.query(Telephony.Sms.Conversations.CONTENT_URI, null, null, null, Telephony.Sms.Conversations.DEFAULT_SORT_ORDER)
        var i = 0
        while (cursor?.moveToNext()!! && i < 1000) {
            val threadId: String = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Conversations.THREAD_ID))
            val contactName: String = findContactByThreadId(threadId)
            val messageCount: String = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Conversations.MESSAGE_COUNT))
            val snippet: String = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Conversations.SNIPPET))
            //val number: String = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
            //val body: String = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
            //listItems.add("Number: $number \nBody: $body")
            listItems.add("Contact: $contactName\nMessage count: $messageCount\nSnippet: $snippet")
            ++i
        }
        cursor.close()
        val adapter: ArrayAdapter<String?> = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, listItems as MutableList<String?>)
        conversationListView?.adapter = adapter
    }

    private fun findContactByThreadId(threadId: String): String {
        val contentResolver: ContentResolver? = activity?.contentResolver
        val cursor: Cursor? = contentResolver?.query(Telephony.Sms.Inbox.CONTENT_URI, null, "thread_id=$threadId", null, null)
        var result = getString(R.string.default_contact_name)
        if (cursor?.count!! > 0) {
            cursor.moveToFirst()
            result = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.ADDRESS))
        }
        cursor.close()
        return result
    }

    companion object {
        val LOG_TAG: String = ConversationListFragment::class.java.simpleName

        fun newInstance(args: Bundle): ConversationListFragment {
            val frag = ConversationListFragment()
            frag.arguments = args
            return frag
        }
    }
}