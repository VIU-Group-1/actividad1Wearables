package com.viu.actividad1.views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Composable para mostar un selector de botones segmentados de cara a cambiar la lista de viajes
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButtons(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {

    Column(modifier = Modifier.padding(16.dp)) {
        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, option ->
                SegmentedButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size)
                ) {
                    Text(text = option)
                }
            }
        }
    }
}