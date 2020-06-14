package ssilvalucas.botservice.data.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ssilvalucas.botservice.model.entity.FinishTravel

interface FinishTravelRepository : MongoRepository<FinishTravel, String>