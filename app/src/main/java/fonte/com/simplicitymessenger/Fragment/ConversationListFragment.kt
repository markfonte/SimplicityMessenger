package fonte.com.simplicitymessenger.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fonte.com.simplicitymessenger.R

class ConversationListFragment : Fragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.conversation_list_fragment, null)
    }

    companion object {
        fun newInstance(args: Bundle) : ConversationListFragment {
            val frag = ConversationListFragment()
            frag.arguments = args
            return frag
        }
    }
}