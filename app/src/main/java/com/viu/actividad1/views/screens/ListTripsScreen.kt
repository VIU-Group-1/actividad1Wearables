package com.viu.actividad1.views.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.tertiaryLight
import com.viu.actividad1.R
import com.viu.actividad1.domain.TripEntity
import com.viu.actividad1.views.components.SegmentedButtons
import com.viu.actividad1.views.components.TripRow
import com.viu.actividad1.views.viewmodels.TripListViewModel
import java.text.SimpleDateFormat
import java.util.Locale

//Pantalla con la lista de todos los viajes
@Composable
fun ListTripsScreen(
    navController: NavController,
    viewModel: TripListViewModel
) {
    val options = listOf("Activos", "Realizados")
    val tripToEdit: TripEntity? = null
    var selectedOption: String by remember { mutableStateOf(options[0]) };

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.NewTripScreen.route) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var trips = viewModel.getTrips(selectedOption != "Activos");
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.title),
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
            SegmentedButtons(selectedOption = selectedOption,
                options,
                onOptionSelected = { newOption ->
                    selectedOption = newOption
                    trips = viewModel.getTrips(selectedOption != "Activos");
                }
            )
            LazyColumn(modifier = Modifier.padding(contentPadding)) {
                trips.forEach { trip ->
                    item {
                        TripRow(trip, navController)
                    }
                }
            }
        }
    }
}

fun formatDate(date: java.util.Date): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

