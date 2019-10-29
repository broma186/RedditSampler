package com.example.redditsampler

import android.app.Application
import dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class RedditSamplerApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.create().inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector;
    }
}