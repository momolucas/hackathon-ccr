package ssilvalucas.botservice.service

import org.springframework.stereotype.Service
import ssilvalucas.botservice.data.repository.FinishTravelRepository
import ssilvalucas.botservice.mapper.FinishTravelMapper
import ssilvalucas.botservice.model.entity.FinishTravel
import ssilvalucas.botservice.web.dto.request.FinishTravelRequest

@Service
class FinishTravelService(val repository: FinishTravelRepository, val mapper: FinishTravelMapper) {

    fun save(driver: String, request: FinishTravelRequest): FinishTravel {
        return repository.save(mapper.requestToEntity(driver, request))
    }

    fun findAll(): MutableList<FinishTravel> = repository.findAll()
}