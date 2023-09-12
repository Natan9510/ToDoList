package com.example.todolist

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todolist.ui.theme.ToDoListTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    //зробити, щоб підраховувало кількість чекнутих айтемів і відображало в хедері

    val TAG: String = "zlo"

    lateinit var recyclerView: RecyclerView

    var myAdapter: MyAdapter =
        MyAdapter(itemCheckedCallBack = { id, isChecked -> updateCheckState(id, isChecked) })

    lateinit var appDataBase: AppDataBase

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: main")
        setContentView(R.layout.activity_main)

        appDataBase =
            Room.databaseBuilder(applicationContext, AppDataBase::class.java, "database-name")
                .build()

        findViewById<Button>(R.id.fab).setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent: Intent = Intent(baseContext, AddNewTaskActivity::class.java)
                startForResult.launch(intent)
            }
        })

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = myAdapter
        subscribeForTodoItems()
    }

    private fun subscribeForTodoItems() {

//        appDataBase.todoDao.observeAllItems().observeWithLifecycle(this, callback = { todoList ->
//            val toDoList = todoList.map {
//                ToDoListItem(it.todoId ?: 0, it.text, it.isChecked)
//            }
//            myAdapter.submitList(toDoList)
//        })

        lifecycleScope.launch {
            // Collect latest Це вставляння труби в потік для отримання останніх данних
            appDataBase.todoDao.observeAllItems()
                .flowWithLifecycle(
                    lifecycle,
                    Lifecycle.State.STARTED
                ) //на якій етапі цикла підписуватись на цей flow
                .distinctUntilChanged() //щоб повідомляло тільки тоді, коли саме по нашій табличці в базі щось мінялось, а не взагалі по всій базі
                .collectLatest { todoListEntities ->
                    Log.d(TAG, "collect: ")

                    val toDoList = todoListEntities.map {
                        TodoBaseListItem.ToDoListItem(it.todoId ?: 0, it.text, it.isChecked)
                    }
                    val count = toDoList.count { it.isChecked }

                    val toDoListSorted: MutableList<TodoBaseListItem> =
                        toDoList.sortedBy { it.isChecked }.toMutableList()

                    //indexOfFirst - Returns index of the first element matching the given predicate, or -1 if the list does not contain such element.
                    val firstCheckedItemPosition =
                        toDoListSorted.indexOfFirst { (it as? TodoBaseListItem.ToDoListItem)?.isChecked == true }

                    if (firstCheckedItemPosition == -1) {
                        // Don't need to add header if there is no any checked item
                    } else {
                        toDoListSorted.add(
                            firstCheckedItemPosition,
                            TodoBaseListItem.CheckedItemsHeader("$count Checked items")
                        )
                    }
                    myAdapter.submitList(toDoListSorted)
                }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
//        fetchAndDisplayTodoItems()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    fun openActivityForResult() {
        val intent = Intent(this, AddNewTaskActivity::class.java)
        startForResult.launch(intent)
    }

    private fun updateCheckState(id: Int, isChecked: Boolean) {
        lifecycleScope.launch {
            appDataBase.todoDao.updateItemCheckedState(id, isChecked)
        }
    }


}

