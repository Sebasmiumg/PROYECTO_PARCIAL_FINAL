package com.example.pendientes

import android.content.Context
import java.util.UUID

/** Almacenamiento temporal en memoria durante el desarrollo. */
object TaskStore {
    private val tasks = mutableListOf<Task>()

    fun getAll(context: Context): List<Task> = tasks.sortedByDescending(Task::createdAt)

    fun find(context: Context, id: String): Task? = tasks.firstOrNull { it.id == id }

    fun add(context: Context, description: String): Task {
        val task = Task(UUID.randomUUID().toString(), description, false, System.currentTimeMillis())
        tasks.add(task)
        return task
    }

    fun setCompleted(context: Context, id: String, completed: Boolean) {
        val index = tasks.indexOfFirst { it.id == id }
        if (index >= 0) tasks[index] = tasks[index].copy(completed = completed)
    }
}
