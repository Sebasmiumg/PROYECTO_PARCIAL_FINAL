package com.example.pendientes

data class Task(
    val id: String,
    val description: String,
    val completed: Boolean,
    val createdAt: Long
)
