package ru.sign6891.chaton

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sign6891.chaton.activities.RegisterActivity
import ru.sign6891.chaton.databinding.ActivityMainBinding
import ru.sign6891.chaton.models.User
import ru.sign6891.chaton.ui.fragments.ChatsFragment
import ru.sign6891.chaton.ui.objects.AppDrawer
import ru.sign6891.chaton.utilits.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFirebase()
        //Функция высшего порядка.
        //Она выполнется доконца, а после запустятся две нижние функции внури
        initUser{
            CoroutineScope(Dispatchers.IO).launch {
                initContacts()
            }

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
        mAppDrawer = AppDrawer()

    }

    override fun onStart() {
        super.onStart()
        AppState.updateState(AppState.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppState.updateState(AppState.OFFLINE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(APP_ACTIVITY, READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            initContacts()
        }
    }
}