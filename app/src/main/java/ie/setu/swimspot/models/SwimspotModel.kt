package ie.setu.swimspot.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class SwimspotModel(var id: Long = 0,
                         var name: String = "",
                         var county: String = "",
                         var categorey: String = "") : Parcelable
