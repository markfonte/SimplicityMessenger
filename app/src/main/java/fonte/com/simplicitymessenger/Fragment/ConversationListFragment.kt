package fonte.com.simplicitymessenger.Fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
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
        val rootView: View =  inflater.inflate(R.layout.conversation_list_fragment, null)
        conversationListView = rootView.findViewById(R.id.conversation_list)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val permissionCheck: Int = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_SMS)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            loadConversationList()
        } else {
            ActivityCompat.requestPermissions(activity as Activity, arrayOf(android.Manifest.permission.READ_SMS), 100)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadConversationList()
            } else {
                Log.e(LOG_TAG, "Cannot be displayed without required permissions")
            }
        }
    }

    private fun loadConversationList() {
        val inboxUri: Uri = Uri.parse("content://sms/inbox")
        val listItems : ArrayList<String?> = ArrayList(0)
        val contentResolver: ContentResolver? = activity?.contentResolver
        val cursor: Cursor? = contentResolver?.query(inboxUri, null, null, null, null)
        while (cursor?.moveToNext()!! ) {
            val number: String = cursor.getString(cursor.getColumnIndexOrThrow("address"))
            val body: String = cursor.getString(cursor.getColumnIndexOrThrow("body"))
            listItems.add("Number: $number \nBody: $body")
        }
        cursor.close()
        val adapter: ArrayAdapter<String?> = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, listItems as MutableList<String?>)
        conversationListView?.adapter = adapter
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