package dev.kevinyu.service.restful.repository;

import dev.kevinyu.service.restful.model.UserDO;
import org.bson.types.ObjectId;

public interface UserRepository extends org.springframework.data.mongodb.repository.MongoRepository<UserDO, ObjectId> {
}
