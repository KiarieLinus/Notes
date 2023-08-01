package com.kiarielinus.notes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@HiltAndroidApp
class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //initialize the Napier in app
        Napier.base(DebugAntilog())
    }
}