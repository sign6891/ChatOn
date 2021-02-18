package ru.sign6891.chaton

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.theartofdev.edmodo.cropper.CropImage
import ru.sign6891.chaton.activities.RegisterActivity
import ru.sign6891.chaton.databinding.ActivityMainBinding
import ru.sign6891.chaton.models.User
import ru.sign6891.chaton.ui.fragments.ChatsFragment
import ru.sign6891.chaton.ui.objects.AppDrawer
import ru.sign6891.chaton.utilits.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFirebase()
        //Функция высшего порядка.
        //Она выполнется доконца, а после запустятся две нижние функции внури
        initUser{
            initFields()
            initFunc()
        }

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

    }

    override fun onStart() {
        super.onStart()
        AppState.updateState(AppState.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppState.updateState(AppState.OFFLINE)
    }
}