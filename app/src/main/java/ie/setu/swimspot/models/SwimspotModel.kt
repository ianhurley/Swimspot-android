package ie.setu.swimspot.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class SwimspotModel(var id: Long = 0,
                         var name: String = "",
                         var county: String = "",
                         var categorey: String = "",
                         var photo: Uri = Uri.EMPTY) : Parcelable
