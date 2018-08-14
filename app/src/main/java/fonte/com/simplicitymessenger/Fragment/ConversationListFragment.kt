package fonte.com.simplicitymessenger.Fragment

import android.annotation.SuppressLint
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
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class ConversationListFragment : Fragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.conversation_list_fragment, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                            Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity!!,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

        }
        val cr = activity?.contentResolver
        val c = cr?.query(Telephony.Sms.Inbox.CONTENT_URI, // Official CONTENT_URI from docs
                arrayOf(Telephony.Sms.Inbox.BODY),
                null, null,
                Telephony.Sms.Inbox.DEFAULT_SORT_ORDER)// Select body text
        // Default sort order

        val totalSMS : Int? = c?.count
        Log.d(LOG_TAG, totalSMS.toString())
        c?.close()
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