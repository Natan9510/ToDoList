package com.example.todolist

import android.widget.TextView
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

@Entity(tableName = "todo_table")
class ToDoItemEntity(
    @PrimaryKey(autoGenerate = true)
    val todoId: Int? = null,
    val text: String,
    var isChecked: Boolean = false,
    val selectedTime: String){

}

