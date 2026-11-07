package com.example.pendientes

import android.content.Context
import java.util.UUID

/** Almacenamiento temporal en memoria durante el desarrollo. */
object TaskStore {
    private val tasks = mutableListOf<Task>()

    fun getAll(context: Context): List<Task> = tasks.sortedByDescending(Task::createdAt)
}
