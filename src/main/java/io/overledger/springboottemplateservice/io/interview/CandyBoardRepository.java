package io.overledger.springboottemplateservice.io.interview;

import io.overledger.springboottemplateservice.io.interview.model.CandyBoardEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CandyBoardRepository extends MongoRepository<CandyBoardEntity, UUID> {
}
