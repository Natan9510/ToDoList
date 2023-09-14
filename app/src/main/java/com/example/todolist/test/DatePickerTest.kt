package com.example.todolist.test

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.todolist.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DatePickerTest: ComponentActivity() {

    lateinit var selectDateTextView: TextView
    lateinit var selectDateButton: Button
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_picker_test)

        selectDateTextView = findViewById(R.id.selected_date_textView)
        selectDateButton = findViewById(R.id.select_date_button)

        selectDateButton.setOnClickListener{
            showDatePicker()
        }

    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            selectDateTextView.text = "Selected Date $formattedDate"
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}