package com.example.todolist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDAO {

    @Query("SELECT * FROM todo_table")
    suspend fun getAll(): List<ToDoItemEntity>

    @Query("SELECT * FROM todo_table")
    fun observeAllItems(): Flow<List<ToDoItemEntity>>

    @Insert
    fun insertAll(vararg todo: ToDoItemEntity)

    @Insert(ToDoItemEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoItemEntity: ToDoItemEntity)



    @Query("DELETE FROM TODO_TABLE")
    fun delete()
}