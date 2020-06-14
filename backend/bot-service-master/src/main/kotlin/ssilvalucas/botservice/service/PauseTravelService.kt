package ssilvalucas.botservice.service

import org.springframework.stereotype.Service
import ssilvalucas.botservice.data.repository.PauseTravelRepository
import ssilvalucas.botservice.mapper.PauseTravelMapper
import ssilvalucas.botservice.model.entity.PauseTravel

@Service
class PauseTravelService(val repository: PauseTravelRepository, val mapper: PauseTravelMapper) {

    fun save(driver: String): PauseTravel {
        return repository.save(mapper.requestToEntity(driver))
    }

    fun findAll(): MutableList<PauseTravel> = repository.findAll()
}