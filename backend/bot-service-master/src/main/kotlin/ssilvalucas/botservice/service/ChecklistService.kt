package ssilvalucas.botservice.service

import org.springframework.stereotype.Service
import ssilvalucas.botservice.data.repository.ChecklistRepository
import ssilvalucas.botservice.mapper.ChecklistMapper
import ssilvalucas.botservice.model.entity.Checklist
import ssilvalucas.botservice.web.dto.request.ChecklistRequest

@Service
class ChecklistService(val repository: ChecklistRepository, val mapper: ChecklistMapper) {

    fun save(driver: String, request: ChecklistRequest): Checklist {
        return repository.save(mapper.requestToEntity(driver, request))
    }

    fun findAll(): MutableList<Checklist> = repository.findAll()
}