package com.example.pendientes

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskDetailActivity : Activity() {
    private var taskId: String? = null
    private lateinit var status: TextView
    private lateinit var description: TextView
    private lateinit var createdAt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        taskId = intent.getStringExtra(EXTRA_TASK_ID)
        if (taskId.isNullOrBlank()) {
            finish()
            return
        }

        status = findViewById(R.id.detailStatus)
        description = findViewById(R.id.detailDescription)
        createdAt = findViewById(R.id.detailCreatedAt)

        findViewById<TextView>(R.id.backToListButton).setOnClickListener { finish() }
        renderTask()
    }

    override fun onResume() {
        super.onResume()
        if (taskId != null && ::status.isInitialized) renderTask()
    }

    private fun renderTask() {
        val task = taskId?.let { TaskStore.find(this, it) }
        if (task == null) {
            finish()
            return
        }

        status.text = if (task.completed) "COMPLETADA" else "PENDIENTE"
        status.setBackgroundResource(
            if (task.completed) R.drawable.success_pill_bg else R.drawable.primary_pill_bg
        )
        status.setTextColor(getColor(if (task.completed) R.color.success else R.color.primary_dark))
        description.text = task.description
        description.paintFlags = if (task.completed) {
            description.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            description.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        val date = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            .format(Date(task.createdAt))
        createdAt.text = "Creada el $date"
    }

    companion object {
        const val EXTRA_TASK_ID = "com.example.pendientes.EXTRA_TASK_ID"
    }
}
