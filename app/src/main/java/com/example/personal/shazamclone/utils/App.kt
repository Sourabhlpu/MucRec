package com.example.personal.shazamclone.utils

import android.app.Application

/**
 * Created by personal on 4/15/2018.
 */
class App : Application() {

    companion object {

        lateinit var instance: App

            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}