package com.viu.actividad1.views.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.primaryLight
import com.example.compose.tertiaryLight
import com.viu.actividad1.R
import com.viu.actividad1.domain.model.Trip
import com.viu.actividad1.views.viewmodels.RateTripViewModel

// Pantalla para la valoración de viajes
@Composable
fun RateTripScreen(
    navController: NavController,
    id: Int,
    viewModel: RateTripViewModel
) {

    var trip by remember { mutableStateOf<Trip?>(null) }

    LaunchedEffect(id) {
        trip = viewModel.getTripById(id)
    }

    Scaffold(
    ) { contentPadding ->

        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón de atrás y título de pantalla
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
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
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Valora el viaje",
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

            var comment by remember {mutableStateOf(TextFieldValue())}
            val maxChar = 100
            var isChecked by remember {mutableStateOf(false)}
            var stars by remember { mutableStateOf(0) }
            val maxStars = 5
            val starColor = Color(0xFFFFC107)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 8.dp)
            ) {
                // Cuadro de texto para comentar el viaje
                OutlinedTextField(
                    value = comment,
                    onValueChange = {
                        if (it.text.length <= maxChar)
                        comment = it },
                    label = { Text("¿Qué tal te ha parecido el viaje?") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp, vertical = 8.dp)
                        .height(150.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${comment.text.length}/$maxChar",
                    modifier = Modifier.align(Alignment.End),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox valoración
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                )
                Text(text = "¿Volverías a realizar este viaje?")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Valoración con estrellas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..maxStars) {
                    Icon(
                        imageVector = if (i <= stars) Icons.Default.Star else Icons.Default.StarOutline,
                        contentDescription = "Star $i",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { stars = i },
                        tint = if (i <= stars) starColor else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para enviar valoración
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = {
                        viewModel.rateTrip(trip, stars, comment.text)
                        navController.navigate(Screen.ListScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Tick",
                            tint = Color.White
                        ) // Icono de tick
                        Text(
                            text = stringResource(R.string.send),
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(
                                fontSize = 22.sp,
                                color = Color.White
                            ) // Texto en blanco
                        )
                    }
                }
            }
        }
    }
}
