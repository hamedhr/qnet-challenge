package io.overledger.springboottemplateservice.io.interview.service;

import io.overledger.springboottemplateservice.io.interview.CandyBoardRepository;
import io.overledger.springboottemplateservice.io.interview.dto.CandyRequest;
import io.overledger.springboottemplateservice.io.interview.dto.CandyResponse;
import io.overledger.springboottemplateservice.io.interview.model.CandyBoardEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CandyService {
    private static final String REG = "(\\w)\\1\\1\\1*";
    private Pattern pattern = Pattern.compile(REG, Pattern.CASE_INSENSITIVE);

    private CandyBoardRepository candyBoardRepository;

    public CandyService(CandyBoardRepository candyBoardRepository) {
        this.candyBoardRepository = candyBoardRepository;
    }

    /**
     * Crushes the input and returns the saved data.
     *
     * @param candyRequest
     * @return CandyResponse
     */
    public CandyResponse crush(CandyRequest candyRequest) {
        CandyBoardEntity board = new CandyBoardEntity();
        board.setId(UUID.randomUUID());
        board.setOriginalValue(candyRequest.getCandyBoard());
        board.setCrushedValue(doCrushNotRecursive(candyRequest.getCandyBoard()));

        CandyBoardEntity save = candyBoardRepository.save(board);
        return new CandyResponse(save.getId(),save.getCrushedValue());
    }

    /**
     * My first approach
     *
     * @param candyBoard
     * @return
     */
   /* public String doCrush(String candyBoard) {
        if (!crushable(candyBoard))
            return candyBoard;
        return doCrush(candyBoard.replaceAll(REG, ""));
    }*/
    public String doCrushNotRecursive(String candyBoard) {
        while (crushable(candyBoard)) {
            candyBoard = candyBoard.replaceAll(REG, "");
        }
        return candyBoard;
    }

    public boolean crushable(String candyBoard) {
        return candyBoard != null
                && candyBoard.length() >= 3
                && pattern.matcher(candyBoard).find();
    }
}
