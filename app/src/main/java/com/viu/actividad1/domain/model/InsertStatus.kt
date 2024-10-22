package com.viu.actividad1.domain.model

sealed class InsertStatus {
    data class Inserted (val message: String): InsertStatus()
    data class Error (val message: String) : InsertStatus()
}

sealed class UpdateStatus {
    data class Updated (val message: String): UpdateStatus()
    data class Error (val message: String) : UpdateStatus()
}

