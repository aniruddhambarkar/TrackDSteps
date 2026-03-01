package com.aniruddhambarkar.trackdsteps.user.di

import dagger.Component

@Component
interface UserComponent {

}

interface UserComponentProvider {
    fun provideUserComponent() : UserComponent
}