package ssilvalucas.botservice.service

import org.springframework.stereotype.Service
import ssilvalucas.botservice.data.repository.StartTravelRepository
import ssilvalucas.botservice.mapper.StartTravelMapper
import ssilvalucas.botservice.model.entity.StartTravel
import ssilvalucas.botservice.web.dto.request.StartTravelRequest

@Service
class StartTravelService(val repository: StartTravelRepository, val mapper: StartTravelMapper) {

    fun save(driver: String, request: StartTravelRequest): StartTravel {
        return repository.save(mapper.requestToEntity(driver, request))
    }

    fun findAll(): MutableList<StartTravel> = repository.findAll()
}