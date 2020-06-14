package ssilvalucas.botservice.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ssilvalucas.botservice.service.AbastecimentoService
import ssilvalucas.botservice.web.dto.request.AbastecimentoRequest

@RestController
@RequestMapping("/api/diario/abastecimento/{driver}")
class AbastecimentoController(val service: AbastecimentoService) {

    @PostMapping
    fun create(@PathVariable driver: String, @RequestBody request: AbastecimentoRequest) =
            ResponseEntity.status(HttpStatus.CREATED).body(service.save(driver, request))

    @GetMapping("list")
    fun fetchAllDrivers() = ResponseEntity.status(HttpStatus.OK).body(service.findAll())
}