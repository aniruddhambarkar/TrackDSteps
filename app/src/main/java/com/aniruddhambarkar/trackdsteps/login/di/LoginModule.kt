package com.aniruddhambarkar.trackdsteps.login.di

import com.aniruddhambarkar.trackdsteps.login.GoogleLoginService
import com.aniruddhambarkar.trackdsteps.login.ILoginService
import com.aniruddhambarkar.trackdsteps.login.presenter.ILoginViewModel
import com.aniruddhambarkar.trackdsteps.login.presenter.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class LoginModule {
    @Binds
    abstract fun provideLoginService(loginService: GoogleLoginService) : ILoginService

    @Binds
    abstract fun provideLoginViewModel(loginViewModel: LoginViewModel) : ILoginViewModel
}