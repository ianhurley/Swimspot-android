package ie.setu.swimspot.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.swimspot.R
import ie.setu.swimspot.databinding.ActivitySwimspotBinding
import ie.setu.swimspot.helpers.showImagePicker
import ie.setu.swimspot.main.MainApp
import ie.setu.swimspot.models.SwimspotModel
import timber.log.Timber
import timber.log.Timber.Forest.i


class SwimspotActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySwimspotBinding
    var swimspot = SwimspotModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false
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
            binding.categorey.setText(swimspot.categorey)
            binding.btnAdd.setText(R.string.save_swimspot)
            Picasso.get()
                .load(swimspot.photo)
                .into(binding.swimspotPhoto)
            if (swimspot.photo != Uri.EMPTY) {
                binding.choosePhoto.setText(R.string.change_swimspot_photo)
            }
        }

        binding.btnAdd.setOnClickListener() {
            swimspot.name = binding.name.text.toString()
            swimspot.county = binding.county.text.toString()
            swimspot.categorey = binding.categorey.text.toString()
            if (swimspot.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_swimspot_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.swimspots.update(swimspot.copy())
                } else {
                    app.swimspots.create(swimspot.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

        binding.choosePhoto.setOnClickListener {
            showImagePicker(imageIntentLauncher)
            i("add photo")
        }

        registerImagePickerCallback()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_swimspot, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
                            swimspot.photo = result.data!!.data!!
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

}