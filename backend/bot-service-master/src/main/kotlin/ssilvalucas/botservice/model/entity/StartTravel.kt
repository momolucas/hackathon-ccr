package ssilvalucas.botservice.model.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "starts")
data class StartTravel(
        val phoneNumber: String,
        val odometroValue: Long,
        @CreatedDate
        var created: LocalDateTime? = null
)