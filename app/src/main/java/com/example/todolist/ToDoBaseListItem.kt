package com.example.todolist

import android.widget.DatePicker
import com.example.todolist.test.ToDoBaseListItem

sealed class TodoBaseListItem{
    data class ToDoListItem(val id: Int, val text: String, val isChecked: Boolean, val selectedTime: String):
        TodoBaseListItem()

    data class CheckedItemsHeader(val text: String): TodoBaseListItem()
}
