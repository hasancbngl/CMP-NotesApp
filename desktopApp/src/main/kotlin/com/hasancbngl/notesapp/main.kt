package com.hasancbngl.notesapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.hasancbngl.notesapp.db.getNoteDatabase

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "NotesApp",
    ) {
        App(
        )
    }
}