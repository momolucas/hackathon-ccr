package ssilvalucas.botservice.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ssilvalucas.botservice.service.ChecklistService
import ssilvalucas.botservice.web.dto.request.ChecklistRequest

@RestController
@RequestMapping("/api/diario/checklist/{driver}")
class ChecklistController(val service: ChecklistService) {

    @PostMapping
    fun create(@PathVariable driver: String, @RequestBody request: ChecklistRequest) =
            ResponseEntity.status(HttpStatus.CREATED).body(service.save(driver, request))

    @GetMapping("list")
    fun fetchAllDrivers() = ResponseEntity.status(HttpStatus.OK).body(service.findAll())
}