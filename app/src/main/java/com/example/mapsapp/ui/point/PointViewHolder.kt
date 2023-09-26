package com.example.mapsapp.ui.point

import androidx.recyclerview.widget.RecyclerView
import com.example.mapsapp.R
import com.example.mapsapp.databinding.PointItemBinding
import com.example.mapsapp.domain.entity.MapPoint

class PointViewHolder(
    private val binding: PointItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun bind(point: MapPoint) {
        binding.coordinatesTextView.text = context.getString(
            R.string.coordinates_text, point.latitude.toString(), point.longitude.toString()
        )
        binding.titleTextView.text = context.getString(R.string.point_with_id, point.id.toString())
    }
}