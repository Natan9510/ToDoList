package com.example.todolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val itemCheckedCallBack: (Int, Boolean) -> Unit):
    ListAdapter<TodoBaseListItem,
        RecyclerView.ViewHolder>(diffCallBack) { //

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is TodoBaseListItem.CheckedItemsHeader -> CHECKED_HEADER_VIEW_TYPE
            is TodoBaseListItem.ToDoListItem -> TODO_ITEM_VIEW_TYPE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            CHECKED_HEADER_VIEW_TYPE -> {
                val viewHeader = LayoutInflater.from(parent.context).inflate(R.layout.checked_header_list_item, parent, false)
                return CheckedItemsHeaderViewHolder(viewHeader)
            }

            TODO_ITEM_VIEW_TYPE -> {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
                return MyViewHolder(view)
            }

            else -> throw IllegalArgumentException("No such view type - $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //можна через when написати, а не через if else
        if(holder is CheckedItemsHeaderViewHolder){
            holder.headerTextView.text = (getItem(position) as TodoBaseListItem.CheckedItemsHeader).text
        }else if(holder is MyViewHolder){
            val toDoListItem = getItem(position) as TodoBaseListItem.ToDoListItem
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
    }

    class MyViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        var textView: TextView = itemView.findViewById(R.id.edit_text)
    }

    class CheckedItemsHeaderViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView){
        val headerTextView: TextView = itemView.findViewById(R.id.header_text)
    }

    companion object{
        private const val TODO_ITEM_VIEW_TYPE = 1
        private const val CHECKED_HEADER_VIEW_TYPE = 2

        private val diffCallBack = object: DiffUtil.ItemCallback<TodoBaseListItem>(){
            override fun areItemsTheSame(
                oldItem: TodoBaseListItem,
                newItem: TodoBaseListItem
            ): Boolean {
                return when(oldItem){
                    is TodoBaseListItem.CheckedItemsHeader -> oldItem.text == (newItem as? TodoBaseListItem.ToDoListItem)?.text
                    is TodoBaseListItem.ToDoListItem -> oldItem.id == (newItem as? TodoBaseListItem.ToDoListItem)?.id
                }
            }

            override fun areContentsTheSame(
                oldItem: TodoBaseListItem,
                newItem: TodoBaseListItem
            ): Boolean {
                return when(oldItem){
                    // Так як в цього айтема немає контенту то завжди передаємо false
                    is TodoBaseListItem.CheckedItemsHeader -> false
                    is TodoBaseListItem.ToDoListItem -> oldItem == (newItem as? TodoBaseListItem.ToDoListItem)
                }
            }
        }
    }
}
