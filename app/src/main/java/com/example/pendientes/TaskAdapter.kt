package com.example.pendientes

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val onOpen: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val tasks = mutableListOf<Task>()

    fun submitList(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(row)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        private val taskStatus: TextView = itemView.findViewById(R.id.taskStatus)

        fun bind(task: Task) {
            taskDescription.text = task.description
            taskDescription.paintFlags = if (task.completed) {
                taskDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                taskDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            taskStatus.text = if (task.completed) "Completada" else "Pendiente"
            taskStatus.setTextColor(
                itemView.context.getColor(if (task.completed) R.color.success else R.color.muted)
            )

            itemView.setOnClickListener { onOpen(task) }
        }
    }
}
