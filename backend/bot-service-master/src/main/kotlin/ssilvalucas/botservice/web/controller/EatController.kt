package ssilvalucas.botservice.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ssilvalucas.botservice.service.EatService

@RestController
@RequestMapping("/api/diario/eat/{phoneNumber}")
class EatController(val service: EatService) {

    @PostMapping
    fun create(@PathVariable phoneNumber: String) =
            ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(phoneNumber))

    @GetMapping("list")
    fun fetchAllDrivers() = ResponseEntity.status(HttpStatus.OK).body(service.findAll())
}