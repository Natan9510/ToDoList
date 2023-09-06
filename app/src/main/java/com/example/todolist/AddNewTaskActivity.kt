package com.example.todolist

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import kotlinx.coroutines.launch

class AddNewTaskActivity : ComponentActivity() {

    val TAG: String = "zlo"

    var saveButton: Button? = null
    lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_todo_item)

        Log.d(TAG, "onCreate: add new task activity")

        saveButton = findViewById(R.id.save_button)
        editText = findViewById(R.id.editText)

        val db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "database-name")
            .build()


        val taskDao: ToDoDAO = db.todoDao

        saveButton?.setOnClickListener {
            lifecycleScope.launch {
                taskDao.insert(ToDoItemEntity(text = editText.text.toString()))
            }
            finish()
        }

//зробити, щоб по кнопці save зберігалось в рекуклер

    }
}