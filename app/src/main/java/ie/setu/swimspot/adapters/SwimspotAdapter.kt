package ie.setu.swimspot.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.swimspot.databinding.CardSwimspotBinding
import ie.setu.swimspot.models.SwimspotModel


class SwimspotAdapter constructor(private var swimspots: List<SwimspotModel>) :
    RecyclerView.Adapter<SwimspotAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSwimspotBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val swimspot = swimspots[holder.adapterPosition]
        holder.bind(swimspot)
    }

    override fun getItemCount(): Int = swimspots.size

    class MainHolder(private val binding : CardSwimspotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(swimspot: SwimspotModel) {
            binding.name.text = swimspot.name
            binding.county.text = swimspot.county
            binding.categorey.text = swimspot.categorey
        }
    }

}