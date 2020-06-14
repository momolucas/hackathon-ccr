package ssilvalucas.botservice.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ssilvalucas.botservice.service.FinishTravelService
import ssilvalucas.botservice.web.dto.request.FinishTravelRequest

@RestController
@RequestMapping("/api/diario/finish/{phoneNumber}")
class FinishTravelController(val service: FinishTravelService) {

    @PostMapping
    fun create(@PathVariable phoneNumber: String, @RequestBody request: FinishTravelRequest) =
            ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(phoneNumber, request))

    @GetMapping("list")
    fun fetchAllDrivers() = ResponseEntity.status(HttpStatus.OK).body(service.findAll())

}