package com.example.lifescore

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import java.time.LocalDate
import java.util.*


class `MainActivity` : AppCompatActivity() {

    private lateinit var datePicker: DatePicker
    private lateinit var btnAddRating: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datePicker = findViewById(R.id.datePicker)
        btnAddRating = findViewById(R.id.btnAddRating)

        btnAddRating.setOnClickListener {
                val day = datePicker.dayOfMonth
                val month = datePicker.month
                val year = datePicker.year

                val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day)
                showRatingDialog(selectedDate)
        }
    }

    private fun showRatingDialog(selectedDate: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Rate Date")

        val ratingOptions = arrayOf("-1", "0", "1")
        val selectedRating = 0

        builder.setSingleChoiceItems(ratingOptions, selectedRating) { dialog, which ->
            val rating = ratingOptions[which].toInt()
            Toast.makeText(this, "Selected Rating: $rating", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") {dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}