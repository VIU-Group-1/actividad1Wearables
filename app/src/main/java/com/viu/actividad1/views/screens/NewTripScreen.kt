package com.viu.actividad1.views.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.viu.actividad1.views.viewmodels.NewTripViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.tertiaryLight
import com.viu.actividad1.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.viu.actividad1.domain.TripEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun NewTripScreen(
    navController: NavController,
    viewModel: NewTripViewModel
) {
    Scaffold(

    ){ contentPadding ->

        Column(modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.newTrip),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = tertiaryLight
                    )
                )
                Icon(
                    imageVector = Icons.Default.Luggage,
                    contentDescription = "Icono de maleta",
                    modifier = Modifier.size(24.dp),
                    tint = tertiaryLight
                )

            }

            var title by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título del viaje") }
            )

            var city by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Ciudad") }
            )

            var country by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = country,
                onValueChange = { country = it },
                label = { Text("País") }
            )

            var departureDate = showCalendar("Fecha de comienzo");
            var returnDate = showCalendar("Fecha de regreso");

            var description by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") }
            )

            var photoUrl by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = photoUrl,
                onValueChange = { photoUrl = it },
                label = { Text("Foto") }
            )

            var cost by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = cost,
                onValueChange = { cost = it },
                label = { Text("Coste") }
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                            viewModel.AddTrip(title.text, city.text, country.text, TripEntity.convertDateToLong(departureDate), TripEntity.convertDateToLong(returnDate), description.text, photoUrl.text, cost.text.toDouble(), false, null)
                            navController.navigate(Screen.ListScreen.route)
                          },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Guardar viaje")
            }
        }

    }
}

@Composable
fun showCalendar(text: String): Date {

    // Date
    var date: Date = Date()
    var dateText by remember { mutableStateOf("DD/MM/yyyy") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
            date = selectedDate.time;
            dateText = sdf.format(selectedDate.time)
        }, year, month, day
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            value = TextFieldValue(dateText),
            onValueChange = { },
            label = { Text(text) },
            readOnly = true,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    datePickerDialog.show()
                }
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Calendar Icon
        IconButton(onClick = { datePickerDialog.show() }) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Select Date",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
    return date;
}


