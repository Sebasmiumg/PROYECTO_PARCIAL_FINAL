package com.example.pendientes

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.UUID

/** Local JSON storage backed by SharedPreferences; no account or network is needed. */
object TaskStore {
    private const val PREFS_NAME = "pendientes_local_store"
    private const val KEY_TASKS = "tasks_json"

    fun getAll(context: Context): List<Task> = read(context)
        .sortedByDescending(Task::createdAt)

    fun find(context: Context, id: String): Task? = read(context).firstOrNull { it.id == id }

    fun add(context: Context, description: String): Task {
        val tasks = read(context).toMutableList()
        val task = Task(
            id = UUID.randomUUID().toString(),
            description = description,
            completed = false,
            createdAt = System.currentTimeMillis()
        )
        tasks.add(task)
        write(context, tasks)
        return task
    }

    fun setCompleted(context: Context, id: String, completed: Boolean) {
        val tasks = read(context).map { task ->
            if (task.id == id) task.copy(completed = completed) else task
        }
        write(context, tasks)
    }

    fun delete(context: Context, id: String) {
        write(context, read(context).filterNot { it.id == id })
    }

    private fun read(context: Context): List<Task> {
        val raw = context.applicationContext
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_TASKS, null)
            ?: return emptyList()

        return try {
            val array = JSONArray(raw)
            buildList(array.length()) {
                for (index in 0 until array.length()) {
                    val item = array.optJSONObject(index) ?: continue
                    val id = item.optString("id")
                    val description = item.optString("description").trim()
                    if (id.isNotBlank() && description.isNotBlank()) {
                        add(
                            Task(
                                id = id,
                                description = description,
                                completed = item.optBoolean("completed", false),
                                createdAt = item.optLong("createdAt", 0L)
                            )
                        )
                    }
                }
            }
        } catch (_: JSONException) {
            emptyList()
        }
    }

    private fun write(context: Context, tasks: List<Task>) {
        val array = JSONArray()
        tasks.forEach { task ->
            array.put(
                JSONObject()
                    .put("id", task.id)
                    .put("description", task.description)
                    .put("completed", task.completed)
                    .put("createdAt", task.createdAt)
            )
        }

        context.applicationContext
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_TASKS, array.toString())
            .apply()
    }
}
