package com.viu.actividad1.views.screens

// Conjunto de rutas de las pantallas para ser usado en el navController
sealed class Screen(val route: String) {
    data object ListScreen : Screen("lista_screen");
    data object NewTripScreen : Screen("new_trip_screen");
    data object DetailsTripScreen : Screen("details_trip_screen")
    data object RateTripScreen : Screen("rate_trip_screen")
}
