package com.viu.actividad1.domain.model

// Modelos relativos al modelo utilizado por la API de los paises
data class Country(
    val name: Name,
    val translations: Map<String, Translation>?
)

data class Name(
    val common: String
)

data class Translation(
    val common: String
)