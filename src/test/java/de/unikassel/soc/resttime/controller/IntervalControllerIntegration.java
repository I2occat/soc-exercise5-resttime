package de.unikassel.soc.resttime.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebFluxTest(IntervalController.class)
public class IntervalControllerIntegration {
    @Autowired
    WebTestClient webTestClient;

    final String TIME_PATH = "/time";

    @Test
    public void streamTime() {
        FluxExchangeResult<String> stringFlux = webTestClient.get().uri(TIME_PATH)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class);

        StepVerifier.create(stringFlux.getResponseBody())
                .expectSubscription()
                .consumeNextWith(response -> testDate(response))
                .consumeNextWith(response -> testDate(response))
                .consumeNextWith(response -> testDate(response))
                .consumeNextWith(response -> testDate(response))
                .thenCancel()
                .verify();
    }

    String previous = null;
    private void testDate(String response) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        assertNotEquals(previous, response);
        assertEquals(dtf.format(now), response);
        previous = response;
    }
}