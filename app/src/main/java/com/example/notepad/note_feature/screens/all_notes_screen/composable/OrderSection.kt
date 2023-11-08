package com.example.notepad.note_feature.screens.all_notes_screen.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notepad.note_feature.domain.util.NoteOrderByFilter
import com.example.notepad.note_feature.domain.util.OrderTypeFilter

/**
 * A Composable that displays the filter options using filter radio buttons.
 */
@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrderByFilter: NoteOrderByFilter = NoteOrderByFilter.Title(OrderTypeFilter.Ascending),
    onOrderChange: (NoteOrderByFilter) -> Unit,
) {
    Column(modifier = modifier) {
        // Row for sort options like title , date, color.
        Row(modifier = Modifier.fillMaxWidth()) {
            FilterRadioButton(
                text = "Title",
                selected = noteOrderByFilter is NoteOrderByFilter.Title,
                onSelected = { onOrderChange(NoteOrderByFilter.Title(noteOrderByFilter.orderTypeFilter)) })

            Spacer(modifier = Modifier.width(8.dp))

            FilterRadioButton(
                text = "Date",
                selected = noteOrderByFilter is NoteOrderByFilter.Date,
                onSelected = { onOrderChange(NoteOrderByFilter.Date(noteOrderByFilter.orderTypeFilter)) })

            Spacer(modifier = Modifier.width(8.dp))


            FilterRadioButton(
                text = "Color",
                selected = noteOrderByFilter is NoteOrderByFilter.Color,
                onSelected = { onOrderChange(NoteOrderByFilter.Color(noteOrderByFilter.orderTypeFilter)) })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Row for filter options like Ascending and descending.
        Row(modifier = Modifier.fillMaxWidth()) {
            FilterRadioButton(
                text = "Ascending",
                selected = noteOrderByFilter.orderTypeFilter is OrderTypeFilter.Ascending,
                onSelected = { onOrderChange(noteOrderByFilter.changeOrderType(OrderTypeFilter.Ascending)) })

            Spacer(modifier = Modifier.width(8.dp))

            FilterRadioButton(
                text = "Descending",
                selected = noteOrderByFilter.orderTypeFilter is OrderTypeFilter.Descending,
                onSelected = { onOrderChange(noteOrderByFilter.changeOrderType(OrderTypeFilter.Descending)) })
        }

    }

}