package com.aniruddhambarkar.trackdsteps.login.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aniruddhambarkar.trackdsteps.common.BaseClass
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ILoginViewModel,BaseClass, ViewModel() {
    override fun login() {
        Log.v(TAG, "LoginViewModel.login() clicked")
    }

}

interface ILoginViewModel{
    fun login()
}