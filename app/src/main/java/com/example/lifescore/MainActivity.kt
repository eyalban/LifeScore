package com.example.lifescore

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var datePicker: DatePicker
    private lateinit var btnAddRating: Button
    private lateinit var tvRating: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datePicker = findViewById(R.id.datePicker)
        btnAddRating = findViewById(R.id.btnAddRating)
        tvRating = findViewById(R.id.tvRating)

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Set a listener for date changes to update the rating display
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
            updateRatingTextView(selectedDate)
        }

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

        val ratingOptions = arrayOf("1", "2", "3", "4", "5")
        val selectedRating = 0

        builder.setSingleChoiceItems(ratingOptions, selectedRating) { dialog, which ->
            val rating = ratingOptions[which].toInt()
            saveRating(selectedDate, rating) // Save the rating
            updateRatingTextView(selectedDate)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun saveRating(date: String, rating: Int) {
        // Save the rating in SharedPreferences
        sharedPreferences.edit {
            putInt(date, rating)
        }
    }

    private fun loadRating(date: String): Int {
        // Load the rating from SharedPreferences, defaulting to 0 if not found
        return sharedPreferences.getInt(date, 0)
    }

    private fun updateRatingTextView(selectedDate: String) {
        val rating = loadRating(selectedDate)
        tvRating.text = "Rating for $selectedDate: ${if (rating == 0) "N/A" else rating}"
    }
}
