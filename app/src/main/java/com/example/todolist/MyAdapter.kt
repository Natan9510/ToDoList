package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MyAdapter(diffCallback: DiffUtil.ItemCallback<ToDoListItem>):
    ListAdapter<ToDoListItem, MyAdapter.MyViewHolder>(diffCallback) {

    var taskList: ArrayList<ToDoListItem>? = null

    class MyViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        var text: TextView = itemView.findViewById(R.id.edit_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var toDoListItem: ToDoListItem = getItem(position)
        holder.checkBox.isChecked = toDoListItem.isChecked
        holder.text.setText(toDoListItem.text)
    }
}
