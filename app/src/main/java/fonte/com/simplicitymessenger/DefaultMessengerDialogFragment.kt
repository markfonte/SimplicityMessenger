package fonte.com.simplicitymessenger

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.support.v4.app.DialogFragment

class DefaultMessengerDialogFragment() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle(R.string.default_messenger_dialog_title)
        alertDialogBuilder.setMessage(R.string.default_messenger_dialog_message)
        alertDialogBuilder.setPositiveButton("OK") { _, _ ->
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, "hello")
            startActivity(intent)
        }
        alertDialogBuilder.setNegativeButton("CANCEL") { _, _ ->
            dismiss()
        }
        return alertDialogBuilder.create()
    }

    companion object {
        val LOG_TAG: String = DefaultMessengerDialogFragment::class.java.simpleName
    }
}