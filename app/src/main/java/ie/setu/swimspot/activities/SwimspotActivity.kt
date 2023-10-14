package ie.setu.swimspot.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.swimspot.databinding.ActivitySwimspotBinding
import ie.setu.swimspot.models.SwimspotModel
import timber.log.Timber
import timber.log.Timber.Forest.i


class SwimspotActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySwimspotBinding
    var swimspot = SwimspotModel()
    val swimspots = ArrayList<SwimspotModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySwimspotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Swimspot Activity started...")

        binding.btnAdd.setOnClickListener() {
            swimspot.name = binding.name.text.toString()
            swimspot.county = binding.county.text.toString()
            swimspot.categorey = binding.categorey.text.toString()
            if (swimspot.name.isNotEmpty()) {
                swimspots.add(swimspot.copy())
                i("add Button Pressed: ${swimspot}")
                for (i in swimspots.indices)
                { i("Swimspot[$i]:${this.swimspots[i]}") }
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}