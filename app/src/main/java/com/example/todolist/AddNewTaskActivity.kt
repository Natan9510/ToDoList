package com.example.todolist

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddNewTaskActivity : ComponentActivity() {

    val TAG: String = "zlo"

    var saveButton: Button? = null
    lateinit var editText: EditText
    lateinit var selectDateTextView: TextView
    lateinit var selectDateButton: Button
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_todo_item)

        Log.d(TAG, "onCreate: add new task activity")

        saveButton = findViewById(R.id.save_button)
        editText = findViewById(R.id.editText)

        selectDateTextView = findViewById(R.id.select_text_view)
        selectDateButton = findViewById(R.id.select_date_button)

        selectDateButton.setOnClickListener{
            showDatePicker()
        }

        val db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "database-name")
            .build()

        val taskDao: ToDoDAO = db.todoDao

        saveButton?.setOnClickListener {
            lifecycleScope.launch {
                taskDao.insert(ToDoItemEntity(text = editText.text.toString(), selectedTime = selectDateTextView.text.toString())) //// toString ?????????????
            }
            finish()
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