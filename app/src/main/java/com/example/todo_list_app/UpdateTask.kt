package com.example.todo_list_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
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

class UpdateTask : AppCompatActivity() {

    private lateinit var database: TodoDatabase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_card)

        database = Room.databaseBuilder(
            applicationContext, TodoDatabase::class.java, "T0_DO_LIST"
        ).build()

        val updateTitle = findViewById<EditText>(R.id.update_title)
        val updatePriority = findViewById<EditText>(R.id.update_priority)
        val updateTaskDueDate = findViewById<EditText>(R.id.updateTask_due_date)
        val deleteBtn = findViewById<Button>(R.id.delete_button)
        val updateBtn = findViewById<Button>(R.id.update_button)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        var selectedStatus = "Active"

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.statusActive -> {
                    selectedStatus = "Active"
                }

                R.id.statusCompleted -> {
                    selectedStatus = "Completed"
                }
            }
        }

        val position = intent.getIntExtra("id", -1)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Due Date")
            .build()

        updateTaskDueDate.setOnClickListener {
            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = selection as Long
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateString = sdf.format(Date(selectedDate))
            updateTaskDueDate.setText(dateString)
        }

        if (position != -1) {
            val task = TaskData.getdata(position)
            val title = task.title
            val priority = task.priority
            val dueDate = task.dueDate

            updateTitle.text = Editable.Factory.getInstance().newEditable(title)
            updatePriority.text = Editable.Factory.getInstance().newEditable(priority)

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dueDateString = sdf.format(dueDate)
            updateTaskDueDate.text = Editable.Factory.getInstance().newEditable(dueDateString)

            deleteBtn.setOnClickListener {
                TaskData.deletedata(position)
                GlobalScope.launch {
                    database.dao().deleteTask(
                        Entity(
                            position + 1,
                            updateTitle.text.toString(),
                            updatePriority.text.toString(),
                            selectedStatus,
                            dueDate
                        )
                    )
                }
                startActivity(Intent(this, MainActivity::class.java))
            }

            updateBtn.setOnClickListener {
                val updatedDueDate = convertStringToDate(updateTaskDueDate.text.toString())

                TaskData.updateData(
                    position,
                    updateTitle.text.toString(),
                    updatePriority.text.toString(),
                    selectedStatus,
                    updatedDueDate
                )

                GlobalScope.launch {
                    database.dao().updateTask(
                        Entity(
                            position + 1,
                            updateTitle.text.toString(),
                            updatePriority.text.toString(),
                            selectedStatus,
                            updatedDueDate
                        )
                    )
                }

                startActivity(Intent(this, MainActivity::class.java))
            }
        } else {
            finish()
        }
    }

    private fun convertStringToDate(dateString: String): Date {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.parse(dateString) ?: Date()
    }
}
