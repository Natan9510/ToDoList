package com.example.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    val TAG: String = "zlo"

    lateinit var recyclerView: RecyclerView
    var diffCallBack: DiffCallBack = DiffCallBack()
    var myAdapter: MyAdapter = MyAdapter(diffCallBack)

    var toDoListItem: ArrayList<ToDoListItem> = ArrayList()

    var saveButton: Button? = null

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

        //data class - todolist item
        //описати все, шо буде відб = id, text, checkbox (стан цього чекбокса - буліан змінна ро дефолту false
        //коли нажим на чекбокс , то взяти елемент списка
        //коли зміню, то треба засабмітити новий список в адаптер

        findViewById<Button>(R.id.fab).setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent: Intent = Intent(baseContext, AddNewTaskActivity::class.java)
                startForResult.launch(intent)
            }
        })

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

//        toDoListItem.add(ToDoListItem(1, "Potato 1", false))
//        toDoListItem.add(ToDoListItem(2, "Potato 2", false))
//        toDoListItem.add(ToDoListItem(3, "Potato 3", true))
//        toDoListItem.add(ToDoListItem(4, "Potato 4", true))

        recyclerView.adapter = myAdapter
        myAdapter.submitList(toDoListItem)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val toDoList = appDataBase.todoDao.getAll().map {
                ToDoListItem(it.todoId ?: 0, it.text, it.isChecked)
            }
            myAdapter.submitList(toDoList)
        }
    }

    fun openActivityForResult() {
        val intent = Intent(this, AddNewTaskActivity::class.java)
        startForResult.launch(intent)
    }
}

