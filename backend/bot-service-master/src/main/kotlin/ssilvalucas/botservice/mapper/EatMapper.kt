package ssilvalucas.botservice.mapper

import org.springframework.stereotype.Component
import ssilvalucas.botservice.model.entity.Eat

@Component
class EatMapper {
    fun requestToEntity(driver: String): Eat {
        return Eat(phoneNumber = driver)
    }
}