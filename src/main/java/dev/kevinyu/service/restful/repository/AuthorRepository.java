package dev.kevinyu.service.restful.repository;

import dev.kevinyu.service.restful.model.AuthorDO;
import org.bson.types.ObjectId;

import java.util.List;

public interface AuthorRepository extends org.springframework.data.mongodb.repository.MongoRepository<AuthorDO, ObjectId> {
    List<AuthorDO> findAuthorDOSByAuthorIdIn(List<ObjectId> authorIds);
}
