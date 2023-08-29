package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
class ToDoItemEntity(
    @PrimaryKey(autoGenerate = true)
    val todoId: Int? = null,
    val text: String,
    val isChecked: Boolean = false){

}

