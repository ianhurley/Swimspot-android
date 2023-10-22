package ie.setu.swimspot.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.setu.swimspot.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_swimspot_photo.toString())
    intentLauncher.launch(chooseFile)
}