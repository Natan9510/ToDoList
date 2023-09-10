package com.example.todolist.test

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.DiffCallBack
import com.example.todolist.MyAdapter
import com.example.todolist.R
import com.example.todolist.ToDoListItem
import java.lang.IllegalArgumentException

class MyAdapterTest(private val itemCheckedCallBack: (Int, Boolean) -> Unit) :
    ListAdapter<ToDoBaseListItem,
            RecyclerView.ViewHolder>(diffCallBack) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ToDoBaseListItem.CheckedItemsHeader -> CHECKED_HEADER_VIEW_TYPE
            is ToDoBaseListItem.ToDoListItem -> TODO_ITEM_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CHECKED_HEADER_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.checked_header_list_item, parent, false)
                CheckedItemsHeaderViewHolder(view)
            }

            TODO_ITEM_VIEW_TYPE -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
                MyViewHolder(view)
            }

            else -> throw IllegalArgumentException("No such view type - $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CheckedItemsHeaderViewHolder) {
            holder.headerTextView.text =
                (getItem(position) as ToDoBaseListItem.CheckedItemsHeader).text
        } else if (holder is MyViewHolder) {
            val toDoListItem = getItem(position) as ToDoBaseListItem.ToDoListItem
            holder.checkBox.isChecked = toDoListItem.isChecked
            holder.textView.text = toDoListItem.text
            if (toDoListItem.isChecked) {
                holder.textView.paintFlags =
                    holder.textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.textView.paintFlags =
                    holder.textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (buttonView.isPressed.not()) return@setOnCheckedChangeListener
                itemCheckedCallBack(toDoListItem.id, isChecked)
            }
        }
    }

    class MyViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        var textView: TextView = itemView.findViewById(R.id.edit_text)
    }

    class CheckedItemsHeaderViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerTextView = itemView.findViewById<TextView>(R.id.header_text)
    }

    // Companion is the same as static for Java
    companion object {
        private const val TODO_ITEM_VIEW_TYPE = 1
        private const val CHECKED_HEADER_VIEW_TYPE = 2

        private val diffCallBack = object : DiffUtil.ItemCallback<ToDoBaseListItem>() {
            override fun areItemsTheSame(
                oldItem: ToDoBaseListItem,
                newItem: ToDoBaseListItem
            ): Boolean {
                return when (oldItem) {
                    is ToDoBaseListItem.CheckedItemsHeader -> oldItem.text == (newItem as? ToDoBaseListItem.CheckedItemsHeader)?.text
                    is ToDoBaseListItem.ToDoListItem -> oldItem.id == (newItem as? ToDoBaseListItem.ToDoListItem)?.id
                }
            }

            override fun areContentsTheSame(
                oldItem: ToDoBaseListItem,
                newItem: ToDoBaseListItem
            ): Boolean {
                return when (oldItem) {
                    // Так як в цього айтема немає контенту то завжди передаємо false
                    is ToDoBaseListItem.CheckedItemsHeader -> false
                    is ToDoBaseListItem.ToDoListItem -> oldItem == (newItem as? ToDoBaseListItem.ToDoListItem)
                }
            }
        }
    }
}
