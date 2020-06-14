package ssilvalucas.botservice.mapper

import org.springframework.stereotype.Component
import ssilvalucas.botservice.model.entity.FinishTravel
import ssilvalucas.botservice.web.dto.request.FinishTravelRequest

@Component
class FinishTravelMapper {
    fun requestToEntity(driver: String, request: FinishTravelRequest): FinishTravel {
        return FinishTravel(
                phoneNumber = driver,
                odometroValue = request.odometroValue
        )
    }
}