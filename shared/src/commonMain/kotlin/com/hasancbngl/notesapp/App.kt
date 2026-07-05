package com.hasancbngl.notesapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hasancbngl.notesapp.model.Note
import com.hasancbngl.notesapp.notes.ListNotesScreen
import com.hasancbngl.notesapp.ui.NotesAppTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

import notesapp.shared.generated.resources.Res
import notesapp.shared.generated.resources.compose_multiplatform
import notesapp.shared.generated.resources.note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val viewModel = viewModel { HomeViewModel() }
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    NotesAppTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showBottomSheet = true
                    },
                    shape = RoundedCornerShape(25)
                ) {
                    Text("+", fontSize = 24.sp)
                }
            }
        ) {
            val notes = viewModel.notes.collectAsStateWithLifecycle()

            Column(modifier = Modifier.padding(it)) {
                Text(
                    "Notes",
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 32.sp
                )
                if (viewModel.notes.value.isNotEmpty()) {
                    ListNotesScreen(notes.value)
                } else EmptyView()
            }

            if (showBottomSheet) {
                ModalBottomSheet(onDismissRequest = {
                    showBottomSheet = false
                }, sheetState = bottomSheetState) {
                    AddItemDialog(
                        onCancel = {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            showBottomSheet = false
                        },
                        onSave = {
                            viewModel.addNotes(it)
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            showBottomSheet = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddItemDialog(
    onCancel: () -> Unit,
    onSave: (Note) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        val color = TextFieldDefaults.colors(
            focusedContainerColor = Transparent,
            unfocusedContainerColor = Transparent
        )

        TextField(
            value = title, onValueChange = { title = it },
            colors = color, modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 22.sp),
            placeholder = {
                Text("Title", fontSize = 22.sp)
            }
        )

        TextField(
            value = description, onValueChange = { description = it },
            colors = color, modifier = Modifier.fillMaxWidth(),
            minLines = 5,
            placeholder = {
                Text("Write the description", fontSize = 22.sp)
            }
        )
        Row(modifier = Modifier.align(Alignment.End)) {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
            Button(onClick = { onSave(Note(title, description)) }) {
                Text("Save")
            }
        }

    }
}

@Composable
fun EmptyView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(Res.drawable.note),
                contentDescription = "icon",
                modifier = Modifier.size(125.dp)
            )
            Text(
                "Create your first note!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 20.sp
            )
        }
    }
}