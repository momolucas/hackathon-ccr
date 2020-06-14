package ssilvalucas.botservice.web.dto.request

data class RegisterExpenseRequest(
        val category: String,
        val cost: Double
)