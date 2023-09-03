package com.example.todolist

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestActivity : ComponentActivity() {

    val scope = CoroutineScope(Job())
    var button1: Button? = null
    var button2: Button? = null
    val TAG = "zlo"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        button1 = findViewById<Button>(R.id.button1)
        button1?.setOnClickListener {

//            runAsync {
//                button1?.text = "done"
//                Log.d(TAG, "callback")
//            }

            runAsync2(object : Callback2{
                override fun runCallback2() {
                    button1?.text = "hello"
                }
            })
        }
        button2 = findViewById<Button>(R.id.button2)
        button2?.setOnClickListener {
//            runAsyncCoroutine()
            lifecycleScope.launch {//запускає корутіну на меін треді. і якщо закрити апчик, то завершаються всі lifecycleScope корутіни актівіті
                runCoroutineLifecycle()
            }
        }
    }

//    fun runAsync(callback: () -> Unit) { // callback з типом повертаємого значення функції
//        Log.d(TAG, "runAsync: start")
//        Thread(Runnable {
//            Thread.sleep(2000)
//            Log.d(TAG, "runAsync: end")
//            runOnUiThread {
//                callback()
//            }
//        }).start()
//    }

    //нижче, як можна написати використовуючи інтерфейс, а не одразу передавати в параметри
    interface Callback2{
        fun runCallback2()
    }

    fun runAsync2 (callback3: Callback2){
        Thread(Runnable {
            Thread.sleep(2000)
            runOnUiThread{
                callback3.runCallback2()
            }
        }).start()
    }

    fun runAsyncCoroutine() {
        Log.d(TAG, "runAsyncCoroutine: start")
        scope.launch { //дефолтна корутіна не визивається на меін треді
            delay(2000)
            Log.d(TAG, "runAsyncCoroutine: end")
            withContext(Dispatchers.Main) {//шоб виконать на меін требді, то писати Dispatchers.Main
                button2?.text = "done"
            }
        }
    }

    suspend fun runCoroutineLifecycle() {
        // suspend fun можна визвати тільки в корутінах
        button2?.text = "running"
        load()
        button2?.text = "done"
    }

    suspend fun load() = withContext(Dispatchers.IO){
        delay(2000) // load from network, for example
    }
}