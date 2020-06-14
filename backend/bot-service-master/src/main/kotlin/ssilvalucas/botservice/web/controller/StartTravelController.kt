package ssilvalucas.botservice.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ssilvalucas.botservice.service.StartTravelService
import ssilvalucas.botservice.web.dto.request.StartTravelRequest

@RestController
@RequestMapping("/api/diario/start/{phoneNumber}")
class StartTravelController(val service: StartTravelService) {

    @PostMapping
    fun create(@PathVariable phoneNumber: String, @RequestBody request: StartTravelRequest) =
            ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(phoneNumber, request))

    @GetMapping("list")
    fun fetchAllDrivers() = ResponseEntity.status(HttpStatus.OK).body(service.findAll())

}