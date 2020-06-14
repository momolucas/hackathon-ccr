package ssilvalucas.botservice.model.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "checklist")
data class Checklist(
        val phoneNumber: String,
        val medidorOleo: String,
        val pneus: String,
        val farois: String,
        val lanternas: String,
        val limpeza: String,
        @CreatedDate
        var created: LocalDateTime? = null
)
