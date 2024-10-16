package com.viu.actividad1.views.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.rememberAsyncImagePainter
import com.example.compose.inversePrimaryLight
import com.example.compose.primaryContainerLight
import com.example.compose.surfaceContainerLight
import com.example.compose.tertiaryLight
import com.viu.actividad1.domain.model.Trip
import com.viu.actividad1.views.screens.formatDate

@Composable
fun TripRow(trip: Trip) {
    Row(
    modifier = Modifier
    .height(100.dp)
    .fillMaxWidth() // Aseguramos que ocupe todo el ancho de la pantalla
    .padding(8.dp)
    .clip(RoundedCornerShape(16.dp))  // Esquinas redondeadas
    .background(primaryContainerLight)

    .clickable {
        print("Hola")
    },
    verticalAlignment = Alignment.CenterVertically
    ){
        Column() {
            Image(
                painter = rememberAsyncImagePainter(trip.photoUrl),
                contentDescription = "Imagen de viaje",
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            bottomStart = 16.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(16.dp)
        ) {
            Text(
                text = trip.title,
                style = TextStyle(
                    color = tertiaryLight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            )
            Text(
                text = "${formatDate(trip.departureDate)} - ${formatDate(trip.returnDate)}",
                maxLines = 1,
                style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                modifier = Modifier.padding(top = 4.dp)
            )

        }

    }
}