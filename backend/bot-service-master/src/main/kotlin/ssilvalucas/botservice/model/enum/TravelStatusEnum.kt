package ssilvalucas.botservice.model.enum

enum class TravelStatusEnum(val descricao: String) {
    INPROGRESS("Viagem em andamento"),
    STOPPED("Parado para descanso"),
    FINISHED("Nenhuma viagem em andamento")
}