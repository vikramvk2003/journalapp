package com.vikram.journalWebsite.Repository;

import com.vikram.journalWebsite.Entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface  UserRepository extends MongoRepository<Users, ObjectId> {
     Users findByUserName(String userName);
     void deleteByUserName(String userName);
}
