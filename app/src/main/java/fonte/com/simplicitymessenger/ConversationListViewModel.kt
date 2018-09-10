package fonte.com.simplicitymessenger

import android.arch.lifecycle.ViewModel

class ConversationListViewModel  internal constructor(
        private val smsQueryRepository: SMSQueryRepository
) : ViewModel() {
    
}