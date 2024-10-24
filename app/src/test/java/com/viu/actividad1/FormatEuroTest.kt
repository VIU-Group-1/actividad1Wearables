package com.viu.actividad1.views.components

import junit.framework.TestCase.assertEquals
import org.junit.Test

class FormatEuroTest {

    @Test
    // Comprobar un float positivo
    fun testFormatEuro() {
        // Entrada y salida esperada
        val input = 1234.56
        val expectedOutput = "1234.56€"

        // Resultado
        val result = FormatEuro(input)

        // Validar si la salida esperada y el resultado es lo mismo
        assertEquals(expectedOutput, result)
    }

    @Test
    // Comprobar cuando es cero
    fun testFormatEuroWithZero() {

        val input = 0.0
        val expectedOutput = "0.00€"

        val result = FormatEuro(input)

        assertEquals(expectedOutput, result)
    }

    @Test
    // Comprobar cuando la entrada es negativa
    fun testFormatEuroWithNegative() {

        val input = -56.78
        val expectedOutput = "-56.78€"

        val result = FormatEuro(input)

        assertEquals(expectedOutput, result)
    }
}