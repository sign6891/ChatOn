package ru.sign6891.chaton.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.sign6891.chaton.MainActivity


open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).mAppDrawer.enableDrawer()
    }

}