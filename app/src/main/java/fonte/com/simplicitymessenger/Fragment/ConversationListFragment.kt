package fonte.com.simplicitymessenger.Fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.provider.Telephony
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fonte.com.simplicitymessenger.R
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.conversation_list_fragment.*
import java.util.jar.Manifest


class ConversationListFragment : Fragment() {
    private var smsList: ArrayList<String>? = null
    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.conversation_list_fragment, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionCheck : Int = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_SMS )
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            loadConversationList()
        }
        else {
            ActivityCompat.requestPermissions(activity as Activity, arrayOf(android.Manifest.permission.READ_SMS), 100)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 100) {
            loadConversationList()
        }
        else {
            Log.e(LOG_TAG, "Cannot be displayed without required permissions")
        }
    }

    private fun loadConversationList() {
        val inboxUri : Uri = Uri.parse("content://sms/inbox")
        smsList = ArrayList(0)
        val contentResolver : ContentResolver? = activity?.contentResolver
        val cursor: Cursor? = contentResolver?.query(inboxUri, null, null, null, null)
        while(cursor?.moveToNext()!!) {
            val number: String = cursor.getString(cursor.getColumnIndexOrThrow("address"))

            val body: String = cursor.getString(cursor.getColumnIndexOrThrow("body"))
            smsList!!.add("Number: $number \nBody: $body")
        }
        cursor.close()
        val adapter: ArrayAdapter<String> = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, smsList )
        //conversation_list.adapter = adapter
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