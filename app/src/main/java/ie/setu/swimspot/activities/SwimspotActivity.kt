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
        binding = ActivitySwimspotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Swimspot Activity started...")

        binding.btnAdd.setOnClickListener() {
            swimspot.name = binding.name.text.toString()
            swimspot.county = binding.county.text.toString()
            swimspot.categorey = binding.categorey.text.toString()
            if (swimspot.name.isNotEmpty()) {
                app.swimspots.add(swimspot.copy())
                i("add Button Pressed: ${swimspot}")
                for (i in app.swimspots.indices)
                { i("Swimspot[$i]:${app.swimspots[i]}") }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a Name", Snackbar.LENGTH_LONG)
                    .show()
            }
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