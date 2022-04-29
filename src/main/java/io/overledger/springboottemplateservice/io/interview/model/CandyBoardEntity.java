package io.overledger.springboottemplateservice.io.interview.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Data
public class CandyBoardEntity {
    @Id
    private UUID id;
    //    @Indexed(unique = true) //TODO hamed:  Probably uniqueness would be useful.
    private String originalValue;
    private String crushedValue;
}
