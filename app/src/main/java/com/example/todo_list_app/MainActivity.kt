package com.example.todo_list_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todo_list_app.adapter.TaskAdapter
import com.example.todo_list_app.database.TodoDatabase
import com.example.todo_list_app.model.TaskData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var database: TodoDatabase

    private lateinit var recyclerView: RecyclerView
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Room.databaseBuilder(
            applicationContext, TodoDatabase::class.java, "T0_DO_LIST"
        ).build()

        val buttonAddTask = findViewById<Button>(R.id.add_task)
        val buttonDeleteTask = findViewById<Button>(R.id.delete_task)
        recyclerView = findViewById(R.id.recycler_view)

        buttonAddTask.setOnClickListener {
            startActivity(Intent(this, CreateTask::class.java))
        }
        setRecycler()
        buttonDeleteTask.setOnClickListener {
            TaskData.deletealldata()

            GlobalScope.launch {
                database.dao().deleteAll()
                database.dao().resetAutoIncrement()
            }
            setRecycler()
        }

    }

    private fun setRecycler() {
        recyclerView.adapter = TaskAdapter(TaskData.getalldata())
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

}