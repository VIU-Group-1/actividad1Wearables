package com.viu.actividad1.domain.model

//Los diferentes estados resultantes de operaciones en BD

//Estado de inserción
sealed class InsertStatus {
    data class Inserted(val message: String) : InsertStatus()
    data class Error(val message: String) : InsertStatus()
}

//Estado de actualización
sealed class UpdateStatus {
    data class Updated(val message: String) : UpdateStatus()
    data class Error(val message: String) : UpdateStatus()
}

