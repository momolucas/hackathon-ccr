package ssilvalucas.botservice.mapper

import org.springframework.stereotype.Component
import ssilvalucas.botservice.model.entity.RegisterExpense
import ssilvalucas.botservice.web.dto.request.RegisterExpenseRequest

@Component
class RegisterExpenseMapper {

    fun requestToEntity(driver: String, request: RegisterExpenseRequest): RegisterExpense {
        return RegisterExpense(
                phoneNumber = driver,
                cost = request.cost,
                category = request.category
        )
    }
}