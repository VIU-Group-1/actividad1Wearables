package com.viu.actividad1.views.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.compose.primaryLight
import com.example.compose.tertiaryLight
import com.viu.actividad1.R
import com.viu.actividad1.domain.model.Trip
import com.viu.actividad1.views.viewmodels.InfoDetailsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

// Pantalla con los detalles de cada viaje
@Composable
fun InfoDetailsScreen(
    navController: NavController,
    id: Int,
    viewModel: InfoDetailsViewModel
) {
    var trip by remember { mutableStateOf<Trip?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val context = LocalContext.current

    LaunchedEffect(id) {
        trip = viewModel.getTripById(id)
        isLoading = false
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = tertiaryLight
                    )
                }
                Text(
                    text = stringResource(R.string.detailstitle),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = tertiaryLight
                    ),
                )
            }
        }
    ) { contentPadding ->
        LazyColumn(modifier = Modifier.padding(contentPadding)) {
            if (isLoading) {
                item {
                    Text(text = "Cargando...", modifier = Modifier.padding(16.dp))
                }
            } else if (trip != null) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = trip!!.title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 16.dp),
                            style = TextStyle(
                                fontSize = 38.sp,
                                fontWeight = FontWeight.Bold,
                                color = tertiaryLight
                            )
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(trip!!.photoUrl),
                            contentDescription = "Imagen de viaje",
                            modifier = Modifier
                                .fillMaxHeight()
                                .height(400.dp)
                                .padding(horizontal = 16.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.departureDate),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = tertiaryLight
                                )
                            )
                            Text(
                                text = dateFormat.format(trip!!.departureDate),
                                modifier = Modifier.padding(top = 16.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            )
                        }
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.returnDate),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = tertiaryLight
                                )
                            )
                            Text(
                                text = dateFormat.format(trip!!.returnDate),
                                modifier = Modifier.padding(top = 16.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 28.dp, end = 28.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(bottom = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.description),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = tertiaryLight
                                )
                            )

                            trip?.review?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.padding(top = 16.dp),
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(bottom = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.cost),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = tertiaryLight
                                )
                            )
                            Text(
                                text = trip!!.cost.toString() + "€",
                                modifier = Modifier.padding(top = 16.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(bottom = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            trip!!.review?.let {
                                Text(
                                    text = stringResource(R.string.rate) + ": " + trip!!.punctuation.toString() + "⭐",
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = tertiaryLight
                                    )
                                )
                            }
                            trip!!.review?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.padding(top = 16.dp),
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 22.dp, end = 22.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botón para completar viaje y pasar a la pantalla de valoración
                        Button(
                            onClick = {
                                navController.navigate(Screen.RateTripScreen .route+ "/${id}")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "Tick",
                                    tint = Color.White
                                ) // Ícono de tick
                                Text(
                                    text = stringResource(R.string.done),
                                    modifier = Modifier.padding(start = 8.dp),
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        color = Color.White
                                    ) // Texto en blanco
                                )
                            }
                        }
                        // Botón para cancelar viaje
                        Button(
                            onClick = {
                                viewModel.showDeleteDialog()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Cancel,
                                    contentDescription = "Cancelar",
                                    tint = Color.White
                                ) // Ícono de cancelar
                                Text(
                                    text = stringResource(R.string.cancel),
                                    modifier = Modifier.padding(start = 8.dp),
                                    style = TextStyle(fontSize = 22.sp, color = Color.White)
                                )
                            }
                        }

                    }
                }
            }
        }
        // Confirmación de eliminación de viaje
        if (viewModel.showDeleteDialog.value) {
            AlertDialog(
                onDismissRequest = { viewModel.hideDeleteDialog() },
                title = {
                    Text(text = "Confirmar eliminación")
                },
                text = {
                    Text(text = "¿Estás seguro de que deseas eliminar este viaje?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteTripById(trip!!.id)
                            viewModel.hideDeleteDialog()
                            Toast.makeText(context, "Viaje eliminado", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()

                        }
                    ) {
                        Text(text = "Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { viewModel.hideDeleteDialog() }
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }

    }

}
