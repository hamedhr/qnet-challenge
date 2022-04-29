package io.overledger.springboottemplateservice.io.interview;

import io.overledger.springboottemplateservice.io.interview.dto.CandyRequest;
import io.overledger.springboottemplateservice.io.interview.dto.CandyResponse;
import io.overledger.springboottemplateservice.io.interview.service.CandyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandyController {

    @Autowired
    private CandyService candyService;

    @PostMapping("/candy")
    public ResponseEntity<CandyResponse> crushCandy(@RequestBody CandyRequest candyRequest) {
        return new ResponseEntity<>(candyService.crush(candyRequest), HttpStatus.OK);
    }

}
