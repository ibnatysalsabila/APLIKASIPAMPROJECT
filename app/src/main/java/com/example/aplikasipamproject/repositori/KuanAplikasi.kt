package com.example.aplikasipamproject.repositori

import android.app.Application

class KuanAplikasi : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = KuanContainer(this)
    }
}