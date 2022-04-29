package io.overledger.springboottemplateservice.io.interview.service;

import io.overledger.springboottemplateservice.io.interview.dto.CandyRequest;
import io.overledger.springboottemplateservice.io.interview.dto.CandyResponse;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
class CandyServiceTest {

    @Autowired
    CandyService candyService;

    @ParameterizedTest
    @MethodSource("crushExpects")
    void doCrush(String input, String expect) {
        CandyRequest req = new CandyRequest();
        req.setCandyBoard(input);
        CandyResponse crushed = candyService.crush(req);
        AssertionsForClassTypes.assertThat(crushed).isNotNull()
                .hasFieldOrPropertyWithValue("result", expect);
    }

    private static Stream<Arguments> crushExpects() {
        return Stream.of(
                Arguments.of("ABCCCB", "ABB"),
                Arguments.of("ABBCCCBA", "AA"),
                Arguments.of("ABCBBAACCCCAAB", "ABC")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"ABCCCB", "ABBCCCBA", "ABCBBAACCCCAAB"})
    void crushable(String input) {
        Assertions.assertThat(candyService.crushable(input))
                .isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "12332", "ABBCCBA", "ABCBBAACCAAB"})
    void non_crushable(String input) {
        Assertions.assertThat(candyService.crushable(input))
                .isFalse();
    }


}