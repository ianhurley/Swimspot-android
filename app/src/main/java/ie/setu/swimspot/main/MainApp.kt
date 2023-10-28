package ie.setu.swimspot.main

import android.app.Application
import ie.setu.swimspot.models.SwimspotJSONStore
import ie.setu.swimspot.models.SwimspotMemStore
import ie.setu.swimspot.models.SwimspotStore

import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    lateinit var swimspots : SwimspotStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        swimspots = SwimspotJSONStore(applicationContext) //using json
        // swimspots = SwimspotMemStore() //using mem
        i("Wild Swimming App started")
    }
}