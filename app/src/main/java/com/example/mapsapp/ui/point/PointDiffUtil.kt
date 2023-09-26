package com.example.mapsapp.ui.point

import androidx.recyclerview.widget.DiffUtil
import com.example.mapsapp.domain.entity.MapPoint

class PointDiffUtil(private val oldList: List<MapPoint>, private val newList: List<MapPoint>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}