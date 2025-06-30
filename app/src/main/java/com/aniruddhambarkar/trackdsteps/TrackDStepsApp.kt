package com.aniruddhambarkar.trackdsteps

import android.app.Application
import com.aniruddhambarkar.trackdsteps.di.AppModule
import com.aniruddhambarkar.trackdsteps.di.DaggerApplicationComponent

class TrackDStepsApp : Application() {

    val appComponent by lazy {
        DaggerApplicationComponent.factory()
            .create(AppModule(this))
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

}