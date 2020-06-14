package ssilvalucas.botservice.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ssilvalucas.botservice.model.entity.Driver
import ssilvalucas.botservice.data.repository.DriverRepository

@RestController
@RequestMapping("api/driver/")
class DriverController(val repository: DriverRepository) {

    @PostMapping("register")
    fun create(@RequestBody driver: Driver) =
            ResponseEntity.ok(repository.save(driver))

    @GetMapping("list")
    fun fetchAllDrivers() = ResponseEntity.status(HttpStatus.CREATED).body(repository.findAll())

    @GetMapping("{phoneNumber}")
    fun fetchDriver(@PathVariable phoneNumber: String) =
            ResponseEntity.ok(repository.findFirstByPhoneNumber(phoneNumber))

    @PutMapping("update/{phoneNumber}")
    fun update(@PathVariable phoneNumber: String, @RequestBody driver: Driver): ResponseEntity<Driver> {
        val driverDB = repository.findFirstByPhoneNumber(phoneNumber).orElseThrow {
            RuntimeException("Driver not found by cellphone: $phoneNumber")
        }
        return ResponseEntity.ok(
                repository.save(driverDB.copy(name = driver.name, phoneNumber = driver.phoneNumber))
        )
    }

    @DeleteMapping("delete/{phoneNumber}")
    fun delete(@PathVariable phoneNumber: String) = repository.findFirstByPhoneNumber(phoneNumber)
            .ifPresent {
                repository.delete(it)
            }
}