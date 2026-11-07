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

        taskAdapter = TaskAdapter(onOpen = ::openDetails, onToggle = ::toggleTask)
        taskList.layoutManager = LinearLayoutManager(this)
        taskList.adapter = taskAdapter

        findViewById<Button>(R.id.addTaskButton).setOnClickListener {
            startActivityForResult(
                Intent(this, AddTaskActivity::class.java),
                REQUEST_ADD_TASK
            )
        }
        refreshTasks()
    }

    override fun onResume() {
        super.onResume()
        if (::taskAdapter.isInitialized) refreshTasks()
    }

    @Deprecated("Kept for broad Android compatibility in this small prototype")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_TASK && resultCode == RESULT_OK) {
            val description = data?.getStringExtra(AddTaskActivity.EXTRA_DESCRIPTION)
                ?.trim()
                .orEmpty()
            if (description.isNotEmpty()) {
                TaskStore.add(this, description)
                refreshTasks()
            }
        }
    }

    private fun refreshTasks() {
        val tasks = TaskStore.getAll(this)
        taskAdapter.submitList(tasks)
        emptyState.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
        taskCount.text = if (tasks.size == 1) "1 tarea" else "${tasks.size} tareas"
    }

    private fun openDetails(task: Task) {
        startActivity(
            Intent(this, TaskDetailActivity::class.java)
                .putExtra(TaskDetailActivity.EXTRA_TASK_ID, task.id)
        )
    }

    private fun toggleTask(id: String) {
        val task = TaskStore.find(this, id) ?: return
        TaskStore.setCompleted(this, id, !task.completed)
        refreshTasks()
    }

    companion object {
        private const val REQUEST_ADD_TASK = 1001
    }
}
