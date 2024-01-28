package com.example.lifescore

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.time.LocalDate
import java.util.*


class `MainActivity` : AppCompatActivity() {

    private lateinit var selectedDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddRating = findViewById<Button>(R.id.btnAddRating)
        btnAddRating.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                selectedDate = LocalDate.of(year, month + 1, day)
                showRatingDialog()
            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.show()
    }

    private fun showRatingDialog() {
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