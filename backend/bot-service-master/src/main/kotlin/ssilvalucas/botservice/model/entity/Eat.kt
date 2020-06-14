package ssilvalucas.botservice.model.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "eat-breaks")
data class Eat(
        val phoneNumber: String,
        @CreatedDate
        var created: LocalDateTime? = null
)