package com.example.pendientes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : Activity() {
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskList: RecyclerView
    private lateinit var emptyState: LinearLayout
    private lateinit var taskCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskList = findViewById(R.id.taskList)
        emptyState = findViewById(R.id.emptyState)
        taskCount = findViewById(R.id.taskCount)

        taskAdapter = TaskAdapter()
        taskList.layoutManager = LinearLayoutManager(this)
        taskList.adapter = taskAdapter

        refreshTasks()
    }

    override fun onResume() {
        super.onResume()
        if (::taskAdapter.isInitialized) refreshTasks()
    }

    private fun refreshTasks() {
        val tasks = TaskStore.getAll(this)
        taskAdapter.submitList(tasks)
        emptyState.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
        taskCount.text = if (tasks.size == 1) "1 tarea" else "${tasks.size} tareas"
    }

}
