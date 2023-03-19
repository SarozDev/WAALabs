package edu.miu.lab5bankmongo.data;

import edu.miu.lab5bankmongo.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account,Integer> {
}
