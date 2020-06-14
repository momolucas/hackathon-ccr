package ssilvalucas.botservice.service

import org.springframework.stereotype.Service
import ssilvalucas.botservice.data.repository.RegisterExpenseRepository
import ssilvalucas.botservice.mapper.RegisterExpenseMapper
import ssilvalucas.botservice.model.entity.RegisterExpense
import ssilvalucas.botservice.web.dto.request.RegisterExpenseRequest

@Service
class RegisterExpenseService(val repository: RegisterExpenseRepository, val mapper: RegisterExpenseMapper) {

    fun save(driver: String, request: RegisterExpenseRequest): RegisterExpense {
        return repository.save(mapper.requestToEntity(driver, request))
    }

    fun findAll(): MutableList<RegisterExpense> = repository.findAll()
}