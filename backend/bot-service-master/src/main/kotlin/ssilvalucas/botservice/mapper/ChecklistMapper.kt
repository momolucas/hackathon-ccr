package ssilvalucas.botservice.mapper

import org.springframework.stereotype.Component
import ssilvalucas.botservice.model.entity.Checklist
import ssilvalucas.botservice.web.dto.request.ChecklistRequest

@Component
class ChecklistMapper {
    fun requestToEntity(driver: String, request: ChecklistRequest): Checklist {
        return Checklist(
                phoneNumber = driver,
                medidorOleo = request.medidorOleo,
                pneus = request.pneus,
                farois = request.farois,
                lanternas = request.lanternas,
                limpeza = request.limpeza
        )
    }
}