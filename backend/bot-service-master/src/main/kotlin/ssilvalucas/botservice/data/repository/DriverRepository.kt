package ssilvalucas.botservice.data.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ssilvalucas.botservice.model.entity.Driver
import java.util.*

@Repository
interface DriverRepository : MongoRepository<Driver, String> {

    fun findFirstByPhoneNumber(phoneNumber: String): Optional<Driver>

}