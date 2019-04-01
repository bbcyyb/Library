package dev.kevinyu.service.restful.repository;

import dev.kevinyu.service.restful.model.BookDO;
import org.bson.types.ObjectId;

public interface BookRepository extends org.springframework.data.mongodb.repository.MongoRepository<BookDO, ObjectId> {
}
