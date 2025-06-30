package com.aniruddhambarkar.trackdsteps.di

import android.app.Application
import com.aniruddhambarkar.trackdsteps.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): ApplicationComponent
    }

    fun inject(app: Application)
    fun inject(mainActivity : MainActivity)

}