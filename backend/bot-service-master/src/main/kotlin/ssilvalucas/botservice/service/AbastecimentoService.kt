package ssilvalucas.botservice.service

import org.springframework.stereotype.Service
import ssilvalucas.botservice.data.repository.AbastecimentoRepository
import ssilvalucas.botservice.mapper.AbastecimentoMapper
import ssilvalucas.botservice.model.entity.Abastecimento
import ssilvalucas.botservice.web.dto.request.AbastecimentoRequest
import ssilvalucas.botservice.web.dto.response.CreatedResource

@Service
class AbastecimentoService(val repository: AbastecimentoRepository, val mapper: AbastecimentoMapper) {

    fun save(driver: String, request: AbastecimentoRequest): CreatedResource {
        val saved = repository.save(mapper.requestToEntity(driver, request))
        return CreatedResource(saved.id)
    }

    fun findAll(): MutableList<Abastecimento> = repository.findAll()
}