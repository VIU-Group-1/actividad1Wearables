package com.viu.actividad1.views.screens

sealed class Screen(val route: String) {
    data object ListScreen: Screen("lista_screen")
}