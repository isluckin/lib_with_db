package com.example.lib_with_db.presentation

import android.app.Application
import com.example.di.AppComponent

class LibraryApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}