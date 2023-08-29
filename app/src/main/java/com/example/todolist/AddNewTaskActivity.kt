package com.example.todolist

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.Room.databaseBuilder

class AddNewTaskActivity: AppCompatActivity() {
    
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

        Thread(Runnable { taskDao.insertAll(ToDoItemEntity(text = editText.text.toString())) })

//зробити, щоб по кнопці save зберігалось в рекуклер

    }
}