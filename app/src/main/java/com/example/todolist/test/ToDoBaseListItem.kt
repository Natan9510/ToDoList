package com.example.todolist.test

sealed class ToDoBaseListItem {
    data class ToDoListItem(val id: Int, val text: String, val isChecked: Boolean) :
        ToDoBaseListItem()
    data class CheckedItemsHeader(val text: String): ToDoBaseListItem()
}