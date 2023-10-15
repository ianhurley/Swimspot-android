package ie.setu.swimspot.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.setu.swimspot.R
import ie.setu.swimspot.databinding.ActivitySwimspotBinding
import ie.setu.swimspot.main.MainApp
import ie.setu.swimspot.models.SwimspotModel
import timber.log.Timber
import timber.log.Timber.Forest.i


class SwimspotActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySwimspotBinding
    var swimspot = SwimspotModel()
    lateinit var app: MainApp

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

}