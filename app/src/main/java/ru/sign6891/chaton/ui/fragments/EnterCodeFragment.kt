package ru.sign6891.chaton.ui.fragments

import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*
import ru.sign6891.chaton.MainActivity
import ru.sign6891.chaton.R
import ru.sign6891.chaton.activities.RegisterActivity
import ru.sign6891.chaton.utilits.*


class EnterCodeFragment(val phoneNumber: String, val id: String) :
    BaseFragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        register_input_code.addTextChangedListener(AppTextWatcher {
            val str: String = register_input_code.text.toString()
            if (str.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code: String = register_input_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_PHONES).child(phoneNumber).setValue(uid)
                    .addOnFailureListener { showToast(it.message.toString()) }
                    .addOnSuccessListener {
                        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                            .addOnSuccessListener {
                                showToast("Добро пожаловать")
                                (activity as RegisterActivity).replaceActivity(MainActivity())
                            }
                            .addOnFailureListener { showToast(it.message.toString()) }
                    }

            } else showToast(it.exception?.message.toString())
        }
    }
}