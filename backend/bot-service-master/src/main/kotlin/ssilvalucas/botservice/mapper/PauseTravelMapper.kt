package ssilvalucas.botservice.mapper

import org.springframework.stereotype.Component
import ssilvalucas.botservice.model.entity.PauseTravel

@Component
class PauseTravelMapper {
    fun requestToEntity(driver: String): PauseTravel {
        return PauseTravel(phoneNumber = driver)
    }
}