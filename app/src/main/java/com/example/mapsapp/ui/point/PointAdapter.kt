package com.example.mapsapp.ui.point

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mapsapp.databinding.PointItemBinding
import com.example.mapsapp.domain.entity.MapPoint

class PointAdapter : RecyclerView.Adapter<PointViewHolder>() {

    private var items: List<MapPoint> = emptyList()

    fun updateItems(newItems: List<MapPoint>) {
        val diffCallback = PointDiffUtil(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val itemBinding =
            PointItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PointViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int =
        items.size

}