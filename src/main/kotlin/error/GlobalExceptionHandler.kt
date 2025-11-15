package com.example.cityparkingplus.error

import com.example.cityparkingplus.parking.BlacklistedPlateException
import com.example.cityparkingplus.parking.CarAlreadyParkedException
import com.example.cityparkingplus.parking.CarNotFoundException
import com.example.cityparkingplus.parking.InvalidPlateFormatException
import com.example.cityparkingplus.parking.ParkingFullException
import com.example.cityparkingplus.parking.ParkingTimeExceededException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ParkingFullException::class)
    fun handleParkingFull(ex: ParkingFullException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            error = ex.message ?: "Parqueadero lleno",
            status = HttpStatus.BAD_REQUEST.value()
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CarAlreadyParkedException::class)
    fun handleCarAlreadyParked(ex: CarAlreadyParkedException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            error = ex.message ?: "Auto ya parqueado",
            status = HttpStatus.BAD_REQUEST.value()
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidPlateFormatException::class)
    fun handleInvalidPlateFormat(ex: InvalidPlateFormatException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            error = ex.message ?: "Formato de placa inválido",
            status = HttpStatus.BAD_REQUEST.value()
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BlacklistedPlateException::class)
    fun handleBlacklistedPlate(ex: BlacklistedPlateException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            error = ex.message ?: "Placa en lista negra",
            status = HttpStatus.FORBIDDEN.value()
        )
        return ResponseEntity(response, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(CarNotFoundException::class)
    fun handleCarNotFound(ex: CarNotFoundException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            error = ex.message ?: "Auto no encontrado",
            status = HttpStatus.NOT_FOUND.value()
        )
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ParkingTimeExceededException::class)
    fun handleParkingTimeExceeded(ex: ParkingTimeExceededException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            error = ex.message ?: "Tiempo de permanencia excedido",
            status = HttpStatus.BAD_REQUEST.value()
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }
}
