package com.example.todolist

import androidx.recyclerview.widget.DiffUtil

class DiffCallBack: DiffUtil.ItemCallback<ToDoListItem>() {
    override fun areItemsTheSame(oldItem: ToDoListItem, newItem: ToDoListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDoListItem, newItem: ToDoListItem): Boolean {
        return oldItem == newItem
    }

}