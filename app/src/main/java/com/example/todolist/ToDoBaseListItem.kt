package com.example.todolist

import com.example.todolist.test.ToDoBaseListItem

sealed class TodoBaseListItem{
    data class ToDoListItem(val id: Int, val text: String, val isChecked: Boolean):
        TodoBaseListItem()

    data class CheckedItemsHeader(val text: String): TodoBaseListItem()
}
