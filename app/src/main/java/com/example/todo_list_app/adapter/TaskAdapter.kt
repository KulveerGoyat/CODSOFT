package com.example.todo_list_app.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list_app.R
import com.example.todo_list_app.UpdateTask
import com.example.todo_list_app.model.CardData
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(private val cardData: List<CardData>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val priority: TextView = itemView.findViewById(R.id.priority)
        val status: TextView = itemView.findViewById(R.id.Mainstatus)
        val taskLayout: FrameLayout = itemView.findViewById(R.id.task_layout)
        val dueDateTextView: TextView = itemView.findViewById(R.id.due_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cardData.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = cardData[position]

        when (currentTask.priority.lowercase(Locale.getDefault())) {
            "high" -> holder.taskLayout.setBackgroundColor(Color.RED)
            "medium" -> holder.taskLayout.setBackgroundColor(Color.YELLOW)
            else -> holder.taskLayout.setBackgroundColor(Color.GREEN)
        }

        if (currentTask.status.lowercase(Locale.getDefault()) == "completed") {
            holder.taskLayout.setBackgroundColor(Color.GRAY)
        }

        holder.title.text = currentTask.title
        holder.priority.text = currentTask.priority
        holder.status.text = currentTask.status

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        holder.dueDateTextView.text = if (currentTask.dueDate != null) {
            sdf.format(currentTask.dueDate)
        } else {
            "No due date"
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateTask::class.java)
            intent.putExtra("id", position)
            holder.itemView.context.startActivity(intent)
        }
    }
}
