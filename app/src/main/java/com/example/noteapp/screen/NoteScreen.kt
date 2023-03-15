package com.example.noteapp.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.components.NoteButton
import com.example.noteapp.components.NoteInputText
import com.example.noteapp.data.NoteDataSource
import com.example.noteapp.model.Note
import com.example.noteapp.util.formatDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun NoteScreen(
    notes:List<Note>,
    onAddNote:(Note)->Unit,
    onRemoveNote:(Note)->Unit
)
{
    var title by remember{
        mutableStateOf("")
    }

    var description by remember{
        mutableStateOf("")
    }

    var context= LocalContext.current

    Column(modifier=Modifier.fillMaxWidth()) {
        TopAppBar(title = {
            Text(text = "Your Notes...")
        },
        actions={
            Icon(imageVector = Icons.Rounded.Notifications,
                contentDescription = "Icon")
        },
        backgroundColor = Color.Gray)


        Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
            NoteInputText(modifier=Modifier.padding(top=9.dp,bottom=8.dp),
                text = title, label = "Title", onTextChange = {
                    if(it.all { char->
                            char.isLetterOrDigit() || char.isWhitespace()

                        })
                        title=it

                })
            NoteInputText(modifier=Modifier.padding(top=9.dp,bottom=8.dp),
                text = description, label = "Add a note", onTextChange = {
                    if(it.all { char->
                            char.isLetterOrDigit() || char.isWhitespace()

                        })
                        description=it
                })
            
            NoteButton(text = "Save", onClick = {
                onAddNote(Note(title=title, description = description
                ))
                title=""
                description=""
                Toast.makeText(context,"Note Added",Toast.LENGTH_SHORT).show()
            })

        }

        Divider(modifier=Modifier.padding(10.dp))
        LazyColumn{
            items(notes){note->
                NoteRow(note = note, onNoteClicked = {
                    onRemoveNote(note)
                })
            }
        }



    }

}





@Composable
fun NoteRow(
    modifier:Modifier=Modifier,
    note:Note,
    onNoteClicked:(Note)->Unit
) {
    Surface(
        modifier = Modifier.padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(),
        color = Color.LightGray ,
        elevation = 6.dp
    ) {

        Column(modifier
            .clickable { onNoteClicked(note) }
            .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.subtitle2
            )
            Text(text = note.description, style = MaterialTheme.typography.subtitle1)
            Text(text = formatDate(note.entryDate.time),
                style = MaterialTheme.typography.caption)

        }


    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview()
{
    NoteScreen(notes = NoteDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {})
}
