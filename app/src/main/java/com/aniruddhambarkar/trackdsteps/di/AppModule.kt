package com.aniruddhambarkar.trackdsteps.di

import android.app.Application
import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import com.aniruddhambarkar.trackdsteps.health.HealthService
import com.aniruddhambarkar.trackdsteps.health.IHealthService
import com.aniruddhambarkar.trackdsteps.home.HomeViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    fun provideApplicationContext(): Context = app.applicationContext

    @Provides
    fun provideHealthConnectClient(): HealthConnectClient = HealthConnectClient.getOrCreate(app.applicationContext)

    @Provides
    fun provideHealthService(healthConnectClient: HealthConnectClient): IHealthService = HealthService(healthConnectClient)

    @Provides
    fun provideHomeViewModel(healthService: IHealthService): HomeViewModel = HomeViewModel(healthService)
}