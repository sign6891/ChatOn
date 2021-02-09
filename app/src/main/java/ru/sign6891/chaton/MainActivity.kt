package ru.sign6891.chaton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import ru.sign6891.chaton.activities.RegisterActivity
import ru.sign6891.chaton.databinding.ActivityMainBinding
import ru.sign6891.chaton.ui.fragments.ChatsFragment
import ru.sign6891.chaton.ui.objects.AppDrawer
import ru.sign6891.chaton.utilits.AUTH
import ru.sign6891.chaton.utilits.initFirebase
import ru.sign6891.chaton.utilits.replaceActivity
import ru.sign6891.chaton.utilits.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }

    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
        initFirebase()
    }
}