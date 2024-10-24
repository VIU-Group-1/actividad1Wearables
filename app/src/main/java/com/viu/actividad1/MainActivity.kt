package com.viu.actividad1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.compose.AppTheme
import com.viu.actividad1.data.TripDatabase
import com.viu.actividad1.data.repository.TripRepository
import com.viu.actividad1.domain.TripEntity
import com.viu.actividad1.views.screens.InfoDetailsScreen
import com.viu.actividad1.views.screens.ListTripsScreen
import com.viu.actividad1.views.screens.NewTripScreen
import com.viu.actividad1.views.screens.RateTripScreen
import com.viu.actividad1.views.screens.Screen
import com.viu.actividad1.views.viewmodels.InfoDetailsViewModel
import com.viu.actividad1.views.viewmodels.NewTripViewModel
import com.viu.actividad1.views.viewmodels.RateTripViewModel
import com.viu.actividad1.views.viewmodels.TripListViewModel

// Clase principal
class MainActivity : ComponentActivity() {
    // Inicialización BD
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TripDatabase::class.java,
            TripDatabase.DATABASE_NAME
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.safeDrawingPadding()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.ListScreen.route,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable(Screen.ListScreen.route) {
                            //Create viewmodel and dao
                            val tripListViewModel = TripListViewModel(TripRepository(db.dao));
                            //Call list component
                            ListTripsScreen(navController, tripListViewModel);
                        }

                        composable(
                            route = "${Screen.NewTripScreen.route}/{tripId}",
                            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val tripId = backStackEntry.arguments?.getInt("tripId")
                            val newTripViewModel = NewTripViewModel(TripRepository(db.dao))
                            var tripToEdit by remember { mutableStateOf<TripEntity?>(null) }
                            LaunchedEffect(tripId) {
                                if (tripId != null) {
                                    tripId?.let {
                                        tripToEdit = newTripViewModel.getTripById(it)
                                    }
                                }
                            }
                            NewTripScreen(navController, newTripViewModel, tripToEdit)
                        }
                        composable(Screen.NewTripScreen.route) {
                            //Create viewmodel and dao
                            val newTripViewModel = NewTripViewModel(TripRepository(db.dao));
                            //Call list component
                            NewTripScreen(navController, newTripViewModel);
                        }
                        composable(
                            route = "${Screen.RateTripScreen.route}/{tripId}",
                            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val tripId = backStackEntry.arguments?.getInt("tripId")
                            //Create viewmodel and dao
                            val rateTripViewModel = RateTripViewModel(TripRepository(db.dao));
                            //Call list component
                            if (tripId != null) {
                                RateTripScreen(navController, tripId, rateTripViewModel)
                            };
                        }
                        composable(
                            "${Screen.DetailsTripScreen.route}/{tripId}",
                            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val tripId = backStackEntry.arguments?.getInt("tripId")

                            //Create viewmodel and dao
                            val infoDetailsViewModel = InfoDetailsViewModel(TripRepository(db.dao));
                            //Call list component
                            if (tripId != null) {
                                InfoDetailsScreen(navController, tripId, infoDetailsViewModel)
                            };
                        }
                    }
                }
            }
        }
    }
}


