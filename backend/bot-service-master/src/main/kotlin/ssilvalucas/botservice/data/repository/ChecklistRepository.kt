package ssilvalucas.botservice.data.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ssilvalucas.botservice.model.entity.Checklist

@Repository
interface ChecklistRepository : MongoRepository<Checklist, String>