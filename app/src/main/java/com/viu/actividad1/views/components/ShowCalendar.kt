package com.viu.actividad1.views.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


// Composable para mostrar el input del calendario
@Composable
fun ShowCalendar(text: String, selectedDate: Date, onDateSelected: (Date) -> Unit) {
    var date by remember { mutableStateOf(selectedDate) }
    var dateText by remember {
        mutableStateOf(
            SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(selectedDate)
        )
    }
    val context = LocalContext.current

    LaunchedEffect(selectedDate) {
        date = selectedDate
        dateText = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate)
    }

    val showDatePicker = {
        // Configura el calendario con la fecha seleccionada
        val calendar = Calendar.getInstance().apply { time = date }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // Crea un nuevo calendario con la fecha seleccionada
                val newDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDayOfMonth)
                }.time

                // Actualiza la fecha y el texto
                date = newDate
                dateText = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(newDate)
                onDateSelected(newDate) // Llama al callback con la nueva fecha
            }, year, month, day
        ).show()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,

        ) {
        TextField(
            value = dateText,
            onValueChange = {},
            label = { Text(text) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 8.dp)
                //.weight(1f)
                .clickable { showDatePicker() },
            trailingIcon = {
                IconButton(onClick = { showDatePicker() }) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Select Date",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )
    }
}