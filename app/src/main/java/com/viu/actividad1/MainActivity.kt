package com.viu.actividad1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.compose.AppTheme
import com.viu.actividad1.data.DAO.TripDao
import com.viu.actividad1.data.TripDatabase
import com.viu.actividad1.data.repository.TripRepository
import com.viu.actividad1.domain.TripEntity
import com.viu.actividad1.domain.model.Trip
import com.viu.actividad1.views.screens.InfoDetailsScreen
import com.viu.actividad1.views.screens.ListTripsScreen
import com.viu.actividad1.views.screens.NewTripScreen
import com.viu.actividad1.views.screens.Screen
import com.viu.actividad1.views.viewmodels.InfoDetailsViewModel
import com.viu.actividad1.views.viewmodels.NewTripViewModel
import com.viu.actividad1.views.viewmodels.TripListViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
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
                    ){
                        composable(Screen.ListScreen.route){
                            //Create viewmodel and dao
                            val tripListViewModel = TripListViewModel(TripRepository(db.dao));
                            //Call list component
                            ListTripsScreen(navController,tripListViewModel);
                        }

                        composable(
                            route = "${Screen.NewTripScreen.route}/{tripId}",
                            arguments = listOf(navArgument("tripId") { type = NavType.IntType})
                        ) { backStackEntry ->
                            val tripId = backStackEntry.arguments?.getInt("tripId")
                            val newTripViewModel = NewTripViewModel(TripRepository(db.dao))
                            var tripToEdit by remember { mutableStateOf<TripEntity?>(null) }
                            LaunchedEffect(tripId) {
                                if(tripId != null) {
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
                            NewTripScreen(navController,newTripViewModel);
                        }
                        composable("${Screen.DetailsTripScreen.route}/{tripId}",
                            arguments = listOf(navArgument("tripId") { type = NavType.IntType})
                        ) { backStackEntry->
                            val tripId = backStackEntry.arguments?.getInt("tripId")

                            //Create viewmodel and dao
                            val infoDetailsViewModel = InfoDetailsViewModel(TripRepository(db.dao));
                            //Call list component
                            if (tripId != null) {
                                InfoDetailsScreen(navController,tripId, infoDetailsViewModel)
                            };
                        }
                    }
                }
            }
        }
    }
}


