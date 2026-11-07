package com.example.pendientes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class AddTaskActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        findViewById<TextView>(R.id.cancelAddButton).setOnClickListener { finish() }
        findViewById<Button>(R.id.saveTaskButton).setOnClickListener {
            val description = descriptionInput.text.toString().trim()
            if (description.isEmpty()) {
                descriptionInput.error = "Escribe una descripción"
                descriptionInput.requestFocus()
                return@setOnClickListener
            }

            setResult(
                RESULT_OK,
                Intent().putExtra(EXTRA_DESCRIPTION, description)
            )
            finish()
        }
    }

    companion object {
        const val EXTRA_DESCRIPTION = "com.example.pendientes.EXTRA_DESCRIPTION"
    }
}
