package com.viu.actividad1.views.screens

import android.app.DatePickerDialog
import android.widget.Toast
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
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
    viewModel: NewTripViewModel,
    tripToEdit: TripEntity?= null
) {
    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var departureDate by remember {
        mutableStateOf( Date())
    }
    var returnDate by remember {
        mutableStateOf( Date())
    }
    var newDate by remember{
        mutableStateOf(tripToEdit?.getDepartureDateAsDate() ?: Date())
    }

    Scaffold(

    ){ contentPadding ->

        Column(modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Atrás",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    tint = tertiaryLight
                )
            }
            val titleResId = if (tripToEdit == null) {
                R.string.newTrip
            } else {
                R.string.editTrip
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = titleResId),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = tertiaryLight
                    ),
                    modifier = Modifier.padding(10.dp)
                )
                Icon(
                    imageVector = Icons.Default.Luggage,
                    contentDescription = "Icono de maleta",
                    modifier = Modifier.size(24.dp),
                    tint = tertiaryLight
                )

            }

            var title by remember { mutableStateOf(TextFieldValue(tripToEdit?.title ?: "")) }
            var city by remember { mutableStateOf(TextFieldValue(tripToEdit?.city ?: "")) }
            var country by remember { mutableStateOf(TextFieldValue(tripToEdit?.country ?: "")) }
            var description by remember { mutableStateOf(TextFieldValue(tripToEdit?.description ?: "")) }
            var photoUrl by remember { mutableStateOf(TextFieldValue(tripToEdit?.photoUrl ?: "")) }
            var cost by remember { mutableStateOf(TextFieldValue(tripToEdit?.cost.toString())) }
            LaunchedEffect(tripToEdit) {
                tripToEdit?.let { trip ->
                    title = TextFieldValue(trip.title ?: "")
                    city = TextFieldValue(trip.city ?: "")
                    country = TextFieldValue(trip.country ?: "")

                    // Asignar fechas de partida y regreso
                    departureDate = trip.getDepartureDateAsDate() ?: Date()
                    returnDate = trip.getReturnDateAsDate() ?: Date()

                    description = TextFieldValue(trip.description ?: "")
                    photoUrl = TextFieldValue(trip.photoUrl ?: "")

                    // Convertir el coste a String
                    cost = TextFieldValue(trip.cost.toString())
                } ?: run {
                    departureDate = Date()
                    returnDate = Date()
                }
            }

            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título del viaje") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 8.dp)
            )
            TextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Ciudad") } ,
                modifier = Modifier
                        .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 8.dp)
            )
            TextField(
                value = country,
                onValueChange = { country = it },
                label = { Text("País") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 8.dp)
            )
            showCalendar("Fecha de comienzo", departureDate) { newDate ->
                departureDate = newDate
            }
            showCalendar("Fecha de regreso", returnDate) { newDate ->
                returnDate = newDate
            }
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 8.dp)
            )
            TextField(
                value = photoUrl,
                onValueChange = { photoUrl = it },
                label = { Text("Foto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 8.dp)
            )
            TextField(
                value = cost,
                onValueChange = { cost = it },
                label = { Text("Coste") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    val costValue = cost.text.toDoubleOrNull()
                    if (costValue != null) {
                        if(tripToEdit == null) {
                            viewModel.AddTrip(
                                title.text,
                                city.text,
                                country.text,
                                TripEntity.convertDateToLong(departureDate),
                                TripEntity.convertDateToLong(returnDate),
                                description.text,
                                photoUrl.text,
                                costValue,
                                false,
                                null
                            )
                        } else{
                            viewModel.UpdateTrip(
                                tripToEdit.id,
                                title.text,
                                city.text,
                                country.text,
                                TripEntity.convertDateToLong(departureDate),
                                TripEntity.convertDateToLong(returnDate),
                                description.text,
                                photoUrl.text,
                                costValue,
                                completed = false,
                                punctuation = null
                            )
                        }
                        navController.navigate(Screen.ListScreen.route)
                    } else {
                        errorMessage = "El coste debe ser un número."
                        showToast = true
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ){
                Text(if (tripToEdit == null) "Guardar viaje" else "Actualizar viaje")
            }
        }
        if (showToast) {
            Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }
}

@Composable
fun showCalendar(text: String, selectedDate: Date, onDateSelected: (Date) -> Unit) {
    var date by remember { mutableStateOf(selectedDate) }
    var dateText by remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate)) }
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