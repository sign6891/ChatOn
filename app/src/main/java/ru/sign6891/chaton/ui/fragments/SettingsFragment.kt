package ru.sign6891.chaton.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.sign6891.chaton.R
import ru.sign6891.chaton.activities.RegisterActivity
import ru.sign6891.chaton.utilits.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {


    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        APP_ACTIVITY.title = "Настройки"
        initFields()
    }

    private fun initFields() {
        settings_bio.text = USER.bio
        settings_full_name.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.state
        settings_username.text = USER.username
        settings_btn_change_username.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        settings_btn_change_bio.setOnClickListener { replaceFragment(ChangeBioFragment()) }
        settings_change_photo.setOnClickListener { changePhotoUser() }
        settings_user_photo.downloadAndSetImage(USER.photoUrl)
    }

    //Манипуляции над изображением Юзера
    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)//Обрезать изображение
            .setCropShape(CropImageView.CropShape.OVAL)// Задаем форму овала
            .start(APP_ACTIVITY, this)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                APP_ACTIVITY.replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK
            && data != null) {
            val uriPhoto = CropImage.getActivityResult(data).uri
            val pathPhoto = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)
            putImageToStorage(uriPhoto, pathPhoto) {
                getUrlFromStorage(pathPhoto){
                    putUrlToDatabase(it){
                        settings_user_photo.downloadAndSetImage(it)
                        showToast(getString(R.string.toast_data_update))
                        USER.photoUrl = it

                    }
                }
            }
            /*pathPhoto.putFile(uriPhoto).addOnCompleteListener { task1 ->
                if (task1.isSuccessful) {
                    pathPhoto.downloadUrl.addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            val photoUrl = task2.result.toString()
                            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(
                                CHILD_PHOTO_URL).setValue(photoUrl)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        settings_user_photo.downloadAndSetImage(photoUrl)
                                        showToast(getString(R.string.toast_data_update))
                                        USER.photoUrl = photoUrl
                                    }
                                }
                        }
                    }
                }
            }*/
        }
    }

}