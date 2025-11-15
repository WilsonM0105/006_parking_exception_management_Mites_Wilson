package com.example.cityparkingplus.parking

import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class ParkingService(
    private val repository: ParkingEntryRepository
) {

    private val plateRegex = Regex("^[A-Z]{3}-\\d{4}$")
    private val blacklistedPlates = setOf("AAA-0001", "BBB-0002")
    private val maxCapacity = 20L
    private val maxHours = 8L

    fun registerEntry(parkingEntry: ParkingEntry): ParkingEntry {
        if (repository.count() >= maxCapacity) {
            throw ParkingFullException("El parqueadero está lleno (capacidad máxima de $maxCapacity autos)")
        }

        if (!plateRegex.matches(parkingEntry.plate)) {
            throw InvalidPlateFormatException("La placa ${parkingEntry.plate} no cumple el formato AAA-1234")
        }

        if (blacklistedPlates.contains(parkingEntry.plate)) {
            throw BlacklistedPlateException("La placa ${parkingEntry.plate} está en la lista negra")
        }

        if (repository.existsByPlate(parkingEntry.plate)) {
            throw CarAlreadyParkedException("El auto con placa ${parkingEntry.plate} ya está parqueado")
        }

        val entryToSave = ParkingEntry(
            plate = parkingEntry.plate,
            ownerName = parkingEntry.ownerName,
            entryTime = LocalDateTime.now()
        )

        return repository.save(entryToSave)
    }

    fun getByPlate(plate: String): ParkingEntry {
        return repository.findByPlate(plate)
            ?: throw CarNotFoundException("No se encontró un auto parqueado con la placa $plate")
    }

    fun registerExit(plate: String): ParkingEntry {
        val entry = repository.findByPlate(plate)
            ?: throw CarNotFoundException("No se encontró un auto parqueado con la placa $plate")

        val hours = Duration.between(entry.entryTime, LocalDateTime.now()).toHours()
        if (hours > maxHours) {
            throw ParkingTimeExceededException("El tiempo de permanencia excede las $maxHours horas")
        }

        repository.delete(entry)
        return entry
    }
}
