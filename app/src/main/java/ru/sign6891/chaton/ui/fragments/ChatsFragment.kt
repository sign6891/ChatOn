package ru.sign6891.chaton.ui.fragments

import androidx.fragment.app.Fragment
import ru.sign6891.chaton.R
import ru.sign6891.chaton.utilits.APP_ACTIVITY

class ChatsFragment : Fragment(R.layout.fragment_chats) {


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "ChatOn"
    }

}