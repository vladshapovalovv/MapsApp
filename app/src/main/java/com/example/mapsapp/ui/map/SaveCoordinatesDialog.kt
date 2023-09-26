package com.example.mapsapp.ui.map

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.mapsapp.R
import com.example.mapsapp.databinding.InputLayoutBinding
import com.example.mapsapp.util.checkFieldsValid

class SaveCoordinatesDialog(
    context: Context,
    initialLatitude: Double,
    initialLongitude: Double,
    private val onSaveClick: (latitude: Double, longitude: Double) -> Unit
) : AlertDialog(context) {

    private var _binding: InputLayoutBinding? = null

    private val binding: InputLayoutBinding
        get() = _binding ?: throw IllegalStateException("Expected InputLayoutBinding, but was null")

    init {
        _binding = InputLayoutBinding.inflate(LayoutInflater.from(context))

        setTitle(context.getString(R.string.coordinates))
        setView(binding.root)
        binding.latitudeEditText.setText(initialLatitude.toString())
        binding.longitudeEditText.setText(initialLongitude.toString())
        binding.positiveButton.setOnClickListener {
            val latitudeText = binding.latitudeEditText.text.toString()
            val longitudeText = binding.longitudeEditText.text.toString()

            if (checkFieldsValid(
                    listOf(binding.latitudeEditText, binding.longitudeEditText),
                    context
                )
            ) {
                val latitude = latitudeText.toDouble()
                val longitude = longitudeText.toDouble()

                onSaveClick.invoke(latitude, longitude)
                dismiss()
            }
        }
        setOnDismissListener { _binding = null }
    }
}

