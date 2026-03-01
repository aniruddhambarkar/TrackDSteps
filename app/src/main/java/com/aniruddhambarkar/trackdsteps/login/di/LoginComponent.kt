package com.aniruddhambarkar.trackdsteps.login.di

import com.aniruddhambarkar.trackdsteps.login.LoginActivity
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {
    fun inject (activity: LoginActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create() : LoginComponent
    }
}