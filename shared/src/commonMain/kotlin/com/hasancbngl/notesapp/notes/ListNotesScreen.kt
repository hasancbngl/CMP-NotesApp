package com.hasancbngl.notesapp.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasancbngl.notesapp.model.Note
import notesapp.shared.generated.resources.Res

@Composable
fun ListNotesScreen(list: List<Note>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) {
            NoteItem(it)
        }
    }
}

fun getRandomColor(): Color {
    return Color(
        (155..255).random(),
        (155..255).random(),
        (155..255).random()
    )
}

@Composable
fun NoteItem(note: Note) {
    Box(
        modifier = Modifier.padding(8.dp).fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(getRandomColor())
            .padding(8.dp)
    ) {
        Text(text = note.title, fontSize = 22.sp)
    }
}

@Composable
@Preview
fun PreviewList() {
    ListNotesScreen(
        listOf(
            Note("this is the first note", "Description"),
            Note("title", "Description"),
            Note("title", "Description"),
        )
    )
}