package com.example.cityparkingplus.parking

import org.springframework.data.jpa.repository.JpaRepository

interface ParkingEntryRepository : JpaRepository<ParkingEntry, Long> {
    fun findByPlate(plate: String): ParkingEntry?
    fun existsByPlate(plate: String): Boolean
}
