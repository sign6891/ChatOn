package ru.sign6891.chaton.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import ru.sign6891.chaton.MainActivity
import ru.sign6891.chaton.R
import ru.sign6891.chaton.activities.RegisterActivity
import ru.sign6891.chaton.utilits.AUTH
import ru.sign6891.chaton.utilits.replaceActivity
import ru.sign6891.chaton.utilits.replaceFragment

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {


    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}