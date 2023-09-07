package com.example.todolist.test

import android.graphics.Paint
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R

class TestCheckbox : ComponentActivity() {

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkbox_layout)

        textView = findViewById(R.id.textView)

        findViewById<CheckBox>(R.id.checkbox)
            .setOnCheckedChangeListener { buttonView, isChecked ->
                if (!textView.paint.isStrikeThruText) {
                    textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textView.paintFlags =
                        textView.paintFlags and (Paint.STRIKE_THRU_TEXT_FLAG.inv())
                }
            }

        //це повинно бути в bind view holder
        //
    }
}