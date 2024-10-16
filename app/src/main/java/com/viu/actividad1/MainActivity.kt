package com.viu.actividad1

import android.os.Bundle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.compose.AppTheme
import com.viu.actividad1.data.DAO.TripDao
import com.viu.actividad1.data.TripDatabase
import com.viu.actividad1.views.screens.ListTripsScreen
import com.viu.actividad1.views.screens.Screen
import com.viu.actividad1.views.viewmodels.TripListViewModel

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

                            val tripListViewModel = TripListViewModel(db.dao);
                            //Call list component
                            ListTripsScreen(navController,tripListViewModel);
                        }
                    }
                }
            }
        }
    }
}


