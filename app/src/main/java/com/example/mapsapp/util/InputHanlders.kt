package com.example.mapsapp.util

import android.content.Context
import com.example.mapsapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun checkFieldsValid(
    editTexts: List<TextInputEditText>,
    context: Context
): Boolean {
    var isValid = true
    for (editText in editTexts) {
        if (editText.text.toString().trim().isEmpty()) {
            val inputLayout = editText.parent.parent as? TextInputLayout
            inputLayout?.error = context.getString(R.string.field_empty)
            isValid = false
        } else {
            val inputLayout = editText.parent.parent as? TextInputLayout
            inputLayout?.error = null
        }
    }
    return isValid
}