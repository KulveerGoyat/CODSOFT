package com.example.todo_list_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.todo_list_app.database.Entity
import com.example.todo_list_app.database.TodoDatabase
import com.example.todo_list_app.model.TaskData
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateTask : AppCompatActivity() {

    private lateinit var database: TodoDatabase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_card)

        database = Room.databaseBuilder(
            applicationContext, TodoDatabase::class.java, "T0_DO_LIST"
        ).build()

        val createtaskText = findViewById<EditText>(R.id.createTask_title)
        val createtaskPriority = findViewById<EditText>(R.id.createTask_priority)
        val createTaskDueDate = findViewById<EditText>(R.id.createTask_due_date)
        val buttonSaveTask = findViewById<Button>(R.id.save_button)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Due Date")
            .build()

        createTaskDueDate.setOnClickListener {
            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = selection as Long
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateString = sdf.format(Date(selectedDate))
            createTaskDueDate.setText(dateString)
        }

        buttonSaveTask.setOnClickListener {
            if (createtaskText.text.toString().isNotEmpty() && createtaskPriority.text.toString().isNotEmpty()) {
                val title = createtaskText.text.toString()
                val priority = createtaskPriority.text.toString()
                val status = "Active"

                val dueDateString = createTaskDueDate.text.toString()
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dueDate: Date = sdf.parse(dueDateString) ?: Date()

                TaskData.setdata(title, priority, status, dueDate)

                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, priority, status, dueDate))
                }

                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}
