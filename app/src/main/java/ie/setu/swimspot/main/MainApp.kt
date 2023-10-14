package ie.setu.swimspot.main

import android.app.Application
import ie.setu.swimspot.models.SwimspotModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val swimspots = ArrayList<SwimspotModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Wild Swimming App started")
        swimspots.add(SwimspotModel("Glendalough", "Wicklow", "Lake"))
        swimspots.add(SwimspotModel("Liffey", "Dublin", "River"))
    }
}