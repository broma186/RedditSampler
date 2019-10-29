package com.example.redditsampler

import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class RedditSamplerApplication : Application(), HasAndroidInjector {

    override fun androidInjector() = androidInjector

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.create().inject(this)

    }
}