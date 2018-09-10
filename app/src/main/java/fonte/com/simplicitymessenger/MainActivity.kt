package fonte.com.simplicitymessenger

import android.os.Bundle
import android.provider.Telephony
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import fonte.com.simplicitymessenger.Fragment.ConversationListFragment
import fonte.com.simplicitymessenger.Fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_main_container, ConversationListFragment.newInstance(Bundle()))
        ft.addToBackStack(ConversationListFragment::class.java.simpleName)
        ft.commit()

        if (Telephony.Sms.getDefaultSmsPackage(this) != packageName) {
            val defaultMessengerDialogFragment = DefaultMessengerDialogFragment()
            defaultMessengerDialogFragment.show(supportFragmentManager, DefaultMessengerDialogFragment.LOG_TAG)
        }


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_chats -> {
                displayChatsFragment()
            }
            R.id.nav_settings -> {
                displaySettingsFragment()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displayChatsFragment() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_main_container, ConversationListFragment.newInstance(Bundle()))
        ft.addToBackStack(ConversationListFragment::class.java.simpleName)
        ft.commit()
    }

    private fun displaySettingsFragment() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_main_container, SettingsFragment.newInstance(Bundle()))
        ft.addToBackStack(SettingsFragment::class.java.simpleName)
        ft.commit()
    }

    companion object {
        val LOG_TAG: String = MainActivity::class.java.simpleName
    }
}
