package com.example.cityparkingplus.parking

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/parking/entries")
class ParkingController(
    private val parkingService: ParkingService
) {

    @PostMapping
    fun registerEntry(@RequestBody parkingEntry: ParkingEntry): ParkingEntry {
        return parkingService.registerEntry(parkingEntry)
    }

    @GetMapping("/{plate}")
    fun getByPlate(@PathVariable plate: String): ParkingEntry {
        return parkingService.getByPlate(plate)
    }

    @DeleteMapping("/{plate}")
    fun registerExit(@PathVariable plate: String): ParkingEntry {
        return parkingService.registerExit(plate)
    }
}
