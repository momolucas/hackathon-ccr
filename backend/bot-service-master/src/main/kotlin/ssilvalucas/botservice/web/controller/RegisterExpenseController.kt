package ssilvalucas.botservice.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ssilvalucas.botservice.service.RegisterExpenseService
import ssilvalucas.botservice.web.dto.request.RegisterExpenseRequest

@RestController
@RequestMapping("/api/financeiro/{phoneNumber}")
class RegisterExpenseController(val service: RegisterExpenseService) {

    @PostMapping
    fun create(@PathVariable phoneNumber: String, @RequestBody request: RegisterExpenseRequest) =
            ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(phoneNumber, request))

    @GetMapping("list")
    fun fetchAllDrivers() = ResponseEntity.status(HttpStatus.OK).body(service.findAll())
}