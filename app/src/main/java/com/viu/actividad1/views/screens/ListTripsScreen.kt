package com.viu.actividad1.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.compose.surfaceContainerLight
import com.example.compose.tertiaryLight
import com.viu.actividad1.views.components.TripRow
import com.viu.actividad1.views.viewmodels.TripListViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ListTripsScreen(
    navController: NavController,
    viewModel: TripListViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        },
    ){ contentPadding ->
        Column(modifier = Modifier.padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally
        ){
            val trips = viewModel.tripsVM.value;
            Row(
                modifier = Modifier
                    .padding(10.dp) // Espaciado alrededor de la fila
                    .fillMaxWidth(), // Asegura que el Row ocupe todo el ancho
                verticalAlignment = Alignment.CenterVertically, // Centra verticalmente los elementos
                horizontalArrangement = Arrangement.Center // Centra horizontalmente los elementos
            ) {
                Text(
                    text = "Mis viajes",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = tertiaryLight
                    )
                )
                Icon(
                    imageVector = Icons.Default.Luggage,
                    contentDescription = "Ícono de maleta",
                    modifier = Modifier.size(24.dp),
                    tint = tertiaryLight
                )

            }
            LazyColumn(modifier = Modifier.padding(contentPadding)){
                trips.forEach{ trip ->
                    item{
                        TripRow(trip)
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