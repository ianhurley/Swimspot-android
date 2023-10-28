package ie.setu.swimspot.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.swimspot.databinding.CardSwimspotBinding
import ie.setu.swimspot.models.SwimspotModel

interface SwimspotListener {
    fun onSwimspotClick(swimspot: SwimspotModel, position : Int)
}

class SwimspotAdapter constructor(private var swimspots: List<SwimspotModel>, private val listener: SwimspotListener) :
    RecyclerView.Adapter<SwimspotAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSwimspotBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val swimspot = swimspots[holder.adapterPosition]
        holder.bind(swimspot, listener)
    }

    override fun getItemCount(): Int = swimspots.size

    class MainHolder(private val binding : CardSwimspotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(swimspot: SwimspotModel, listener: SwimspotListener) {
            binding.name.text = swimspot.name
            binding.county.text = swimspot.county
            binding.categorey.text = swimspot.categorey
            Picasso.get().load(swimspot.photo).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onSwimspotClick(swimspot,adapterPosition)}
        }
    }

}