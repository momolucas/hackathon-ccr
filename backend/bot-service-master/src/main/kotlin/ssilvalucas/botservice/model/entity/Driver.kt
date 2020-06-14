package ssilvalucas.botservice.model.entity

import org.springframework.data.mongodb.core.mapping.Document
import ssilvalucas.botservice.model.enum.TravelStatusEnum

@Document(collection = "drivers")
data class Driver(
        val id: String? = null,
        val name: String,
        val phoneNumber: String,
        val travelInProgress: String = TravelStatusEnum.FINISHED.toString()
)