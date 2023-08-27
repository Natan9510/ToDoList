package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {

    lateinit var recyclerView: RecyclerView
    var diffCallBack: DiffCallBack = DiffCallBack()
    var myAdapter: MyAdapter = MyAdapter(diffCallBack)

    var toDoListItem: ArrayList<ToDoListItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //data class - todolist item
        //описати все, шо буде відб = id, text, checkbox (стан цього чекбокса - буліан змінна ро дефолту false
        //коли нажим на чекбокс , то взяти елемент списка
        //коли зміню, то треба засабмітити новий список в адаптер
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        toDoListItem.add(ToDoListItem(1, "Potato 1", false))
        toDoListItem.add(ToDoListItem(2, "Potato 2", false))
        toDoListItem.add(ToDoListItem(3, "Potato 3", true))
        toDoListItem.add(ToDoListItem(4, "Potato 4", true))

        recyclerView.adapter = myAdapter
        myAdapter.submitList(toDoListItem)
    }
}

