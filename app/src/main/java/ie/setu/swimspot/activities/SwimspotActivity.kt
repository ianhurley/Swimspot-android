package ie.setu.swimspot.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.swimspot.R
import ie.setu.swimspot.databinding.ActivitySwimspotBinding
import ie.setu.swimspot.helpers.showImagePicker
import ie.setu.swimspot.main.MainApp
import ie.setu.swimspot.models.Location
import ie.setu.swimspot.models.SwimspotModel
import timber.log.Timber
import timber.log.Timber.Forest.i


class SwimspotActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySwimspotBinding
    var swimspot = SwimspotModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false

    private val categories = arrayOf("Lake","Pool","River","Sea","Waterfall")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        edit = false

        binding = ActivitySwimspotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Swimspot Activity started...")

        if (intent.hasExtra("swimspot_edit")) {
            edit = true
            swimspot = intent.extras?.getParcelable("swimspot_edit")!!
            binding.name.setText(swimspot.name)
            binding.county.setText(swimspot.county)

            val categoreyPosition = categories.indexOf(swimspot.categorey)
            if (categoreyPosition >= 0) {
                binding.categoreySpinner.setSelection(categoreyPosition)
            }

            binding.btnAdd.setText(R.string.save_swimspot)
            Picasso.get()
                .load(swimspot.photo)
                .into(binding.swimspotPhoto)
            if (swimspot.photo != Uri.EMPTY) {
                binding.choosePhoto.setText(R.string.change_swimspot_photo)
            }
        }

        val categoreySpinner = binding.categoreySpinner
        val categoreyAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories)
        categoreyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoreySpinner.adapter = categoreyAdapter

        binding.btnAdd.setOnClickListener() {
            swimspot.name = binding.name.text.toString()
            swimspot.county = binding.county.text.toString()

            swimspot.categorey = categories[binding.categoreySpinner.selectedItemPosition]

            val inputMaxLength = 20 //to fit input panel

            if (swimspot.name.isEmpty() || swimspot.county.isEmpty() || swimspot.categorey.isEmpty()) {
                Snackbar.make(it, R.string.enter_swimspot_details, Snackbar.LENGTH_LONG).show()
            }else if (swimspot.name.length > inputMaxLength || swimspot.county.length > inputMaxLength || swimspot.categorey.length > inputMaxLength) {
                Snackbar.make(it, R.string.input_length_exceeded, Snackbar.LENGTH_LONG).show()
            }else if (!swimspot.name.matches(Regex("^[a-zA-Z ]*\$")) || !swimspot.county.matches(Regex("^[a-zA-Z ]*\$")) || !swimspot.categorey.matches(Regex("^[a-zA-Z ]*\$"))) {
                Snackbar.make(it, R.string.text_input_only, Snackbar.LENGTH_LONG).show()
            } else {
                if (edit) {
                    app.swimspots.update(swimspot.copy())
                } else {
                    app.swimspots.create(swimspot.copy())
                }
                i("add Button Pressed: $swimspot")
                setResult(RESULT_OK)
                finish()
            }
        }

        binding.choosePhoto.setOnClickListener {
            showImagePicker(imageIntentLauncher, this)
            i("add photo")
        }

        binding.swimspotLocation.setOnClickListener {
            val location = Location(53.4494762, -7.5029786, 6f)
            if (swimspot.zoom != 0f) {
                location.lat =  swimspot.lat
                location.lng = swimspot.lng
                location.zoom = swimspot.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_swimspot, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                setResult(99)
                app.swimspots.delete(swimspot)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            swimspot.photo = image

                            Picasso.get()
                                .load(swimspot.photo)
                                .into(binding.swimspotPhoto)
                            binding.choosePhoto.setText(R.string.change_swimspot_photo)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            swimspot.lat = location.lat
                            swimspot.lng = location.lng
                            swimspot.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}