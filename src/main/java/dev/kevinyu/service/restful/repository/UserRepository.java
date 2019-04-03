package dev.kevinyu.service.restful.repository;

import dev.kevinyu.service.restful.model.UserDO;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface UserRepository extends org.springframework.data.mongodb.repository.MongoRepository<UserDO, ObjectId> {
    Optional<UserDO>  findUserDOByUsername(String username);
        }
