package com.viu.actividad1.views.components


// Formatear en euros con dos decimales y sustituir la coma por el punto
fun FormatEuro(coste: Double): String {
    return String.format("%.2f€", coste).replace(",", ".")
}