package com.viu.actividad1.domain.model

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