package ru.sign6891.chaton.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import ru.sign6891.chaton.MainActivity
import ru.sign6891.chaton.R
import ru.sign6891.chaton.utilits.APP_ACTIVITY
import ru.sign6891.chaton.utilits.hideKeyboard


open class BaseChangeFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        APP_ACTIVITY.mAppDrawer.disableDrawer()
        hideKeyboard()
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        APP_ACTIVITY.menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    open fun change() {

    }

}