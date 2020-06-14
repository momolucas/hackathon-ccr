package ssilvalucas.botservice.model.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "despesas")
data class RegisterExpense(
        val phoneNumber: String,
        val cost: Double,
        val category: String,
        @CreatedDate
        var created: LocalDateTime? = null
)