package com.hasancbngl.notesapp

import androidx.compose.ui.window.ComposeUIViewController
import com.hasancbngl.notesapp.db.getNoteDatabase

fun MainViewController() = ComposeUIViewController {
    App(
        getNoteDatabase(
            getDatabaseBuilder()
        )
    )
}