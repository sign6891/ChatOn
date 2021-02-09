package ru.sign6891.chaton.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import ru.sign6891.chaton.R
import ru.sign6891.chaton.databinding.ActivityRegisterBinding
import ru.sign6891.chaton.ui.fragments.EnterPhoneNumberFragment
import ru.sign6891.chaton.utilits.initFirebase
import ru.sign6891.chaton.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        replaceFragment(EnterPhoneNumberFragment(), false)
    }
}