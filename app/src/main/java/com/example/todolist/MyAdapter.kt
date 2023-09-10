package com.example.todolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val itemCheckedCallBack: (Int, Boolean) -> Unit): ListAdapter<ToDoListItem,
        MyAdapter.MyViewHolder>(DiffCallBack()) {

    //var taskList: ArrayList<ToDoListItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val toDoListItem: ToDoListItem = getItem(position)
        holder.checkBox.isChecked = toDoListItem.isChecked
        holder.textView.text = toDoListItem.text
        if(toDoListItem.isChecked){
            holder.textView.paintFlags = holder.textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            holder.textView.paintFlags = holder.textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed.not()) return@setOnCheckedChangeListener
            itemCheckedCallBack(toDoListItem.id, isChecked)
        }
    }

    class MyViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        var textView: TextView = itemView.findViewById(R.id.edit_text)
    }

}
