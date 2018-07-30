package fonte.com.simplicitymessenger.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fonte.com.simplicitymessenger.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {

    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.settings_fragment, null)
    }


    companion object {
        fun newInstance(args: Bundle) : SettingsFragment {
            val frag = SettingsFragment()
            frag.arguments = args
            return frag
        }
    }
}