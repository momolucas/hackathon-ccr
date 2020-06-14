package ssilvalucas.botservice.data.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ssilvalucas.botservice.model.entity.Abastecimento

@Repository
interface AbastecimentoRepository: MongoRepository<Abastecimento, String> {
}