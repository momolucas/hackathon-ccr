package ssilvalucas.botservice.mapper

import org.springframework.stereotype.Component
import ssilvalucas.botservice.model.entity.StartTravel
import ssilvalucas.botservice.web.dto.request.StartTravelRequest

@Component
class StartTravelMapper {
    fun requestToEntity(driver: String, request: StartTravelRequest): StartTravel {
        return StartTravel(
                phoneNumber = driver,
                odometroValue = request.odometroValue
        )
    }
}