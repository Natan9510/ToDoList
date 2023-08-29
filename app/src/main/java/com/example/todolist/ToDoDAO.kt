package com.example.todolist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoDAO {
    @Query("SELECT * FROM todo_table")
    fun getAll(): List<ToDoItemEntity>

    @Insert
    fun insertAll(vararg todo: ToDoItemEntity)

    @Query("DELETE FROM TODO_TABLE")
    fun delete()
}