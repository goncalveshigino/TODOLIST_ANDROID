package com.example.todolist.components

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CardTask(
    title: String,
    date: String,
    onClick:() -> Unit
) {

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        Row {
            Column {
                Text( text = title, fontWeight = FontWeight.Bold)
                Text(text = date, color = Color.Gray)
            }

            Spacer(Modifier.weight(1f))

            IconButton(onClick = { onClick}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "")
            }
        }

        HorizontalDivider()
    }
}