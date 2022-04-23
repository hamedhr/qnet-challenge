package io.overledger.springboottemplateservice.services;

import io.overledger.springboottemplateservice.controllers.TemplateController;
import io.overledger.springboottemplateservice.dto.TemplateRequest;
import io.overledger.springboottemplateservice.dto.TemplateResponse;
import io.overledger.springboottemplateservice.mongodb.TemplateDocument;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(TemplateController.class)
class TemplateServiceTest {
    public static final String RESOURCE_NAME = "/templates";
    public static final String SAMPLE_VALUE = "hello-spring";

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private TemplateService templateService;

    @Test
    void post_template_isReturnedOk() {
        //Given
        TemplateRequest request = new TemplateRequest();
        request.setTemplateField(SAMPLE_VALUE);
        TemplateResponse response = new TemplateResponse();
        response.setTemplateField(SAMPLE_VALUE);

        Mockito.when(templateService.postStuff(request))
                .thenReturn(Mono.just(response));
        //When
        webClient.post()
                .uri(RESOURCE_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.templateField").isEqualTo(response.getTemplateField())
        ;
    }

    @Test
    void post_howAreYouTemplateField_isReturnedOk() {
        //Given
        TemplateRequest request = new TemplateRequest();
        request.setTemplateField("How are you?");
        TemplateResponse response = new TemplateResponse();
        response.setTemplateField("Always peachy!");
        Mockito.when(templateService.postStuff(request))
                .thenReturn(Mono.just(response));
        //When
        webClient.post()
                .uri(RESOURCE_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.templateField").isEqualTo(response.getTemplateField())
        ;
    }

    @Test
    void get_simple_isReturnedOk() {
        //Given
        TemplateDocument response = new TemplateDocument();
        response.setTemplateField(SAMPLE_VALUE);
        Mockito.when(templateService.getStuff(SAMPLE_VALUE))
                .thenReturn(Mono.just(response));
        //When
        webClient.get()
                .uri(RESOURCE_NAME + "/" + SAMPLE_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.templateField").isEqualTo(response.getTemplateField())
        ;
    }

   /* @Test
    void get_notExist_isReturned400() throws Exception {
        String errorMessage = "The document was not found.";
        //Given
        TemplateDocument response = new TemplateDocument();
        response.setTemplateField(SAMPLE_VALUE);
        Mockito.when(templateService.getStuff(SAMPLE_VALUE))
                .thenReturn(Mono.error(() -> new TemplateException(errorMessage)));
        //When
        webClient.get()
                .uri(RESOURCE_NAME + "/" + SAMPLE_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.errorCode").isEqualTo(42)
                .jsonPath("$.message").isEqualTo("Failed processing the request: " + errorMessage)
        ;
    }
*/
}