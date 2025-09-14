package com.example.restclientpocproductservice.web.it;

import com.example.restclientpocproductservice.domain.model.Product;
import com.example.restclientpocproductservice.domain.repository.ProductRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * End-to-end(ish) test:
 * Controller -> Service -> Repository -> RestClient -> (WireMock) Pricing API -> JSON response.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("restClient") // if your RestClient beans are enabled by this profile
class ProductControllerIT {

    private static final WireMockServer wiremock = new WireMockServer(options().dynamicPort());

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        wiremock.start();
        // RestClient will call {baseUrl}?page=..&size=.. so baseUrl should end with /prices
        registry.add("client.pricing.baseUrl",
                () -> "http://localhost:" + wiremock.port() + "/prices");
    }

    @AfterAll
    static void stopWiremock() {
        wiremock.stop();
    }

    @AfterEach
    void resetWiremock() {
        wiremock.resetAll();
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    private Long idA;
    private Long idB;

    @BeforeEach
    void seedDbAndStubPricing() {
        // Clear any Liquibase-seeded data to ensure predictable results
        productRepository.deleteAll();

        // Create two products; let the DB generate IDs
        Product a = new Product(1L, "A");
        a = productRepository.save(a);
        idA = a.getId();

        Product b = new Product(2L, "B");
        b = productRepository.save(b);
        idB = b.getId();

        // Stub the LIST endpoint that your service is calling: /prices?page=&size=
        String listJson = """
            {
              "data": [
                {"id": %d, "amount": 10.99},
                {"id": %d, "amount": 12.49}
              ]
            }
            """.formatted(idA, idB);

        wiremock.stubFor(
                // Fully-qualify WireMock.get(...) to avoid import clashes with MockMvc's get(...)
                com.github.tomakehurst.wiremock.client.WireMock.get(urlPathEqualTo("/prices"))
                        .withQueryParam("page", matching("\\d+"))
                        .withQueryParam("size", matching("\\d+"))
                        .willReturn(okJson(listJson))
        );

        // (Optional) If your flow also calls /prices/{id}, you can add these too:
        // wiremock.stubFor(
        //   com.github.tomakehurst.wiremock.client.WireMock.get(urlPathEqualTo("/prices/" + idA))
        //     .willReturn(okJson(("{\"data\":{\"id\":"+idA+",\"price\":10.99}}"))));
        // wiremock.stubFor(
        //   com.github.tomakehurst.wiremock.client.WireMock.get(urlPathEqualTo("/prices/" + idB))
        //     .willReturn(okJson(("{\"data\":{\"id\":"+idB+",\"price\":12.49}}"))));
    }

    @Test
    void getProducts_returnsApiResponseWithJoinedData() throws Exception {
        // Use controller default size; we only set page=0 to be explicit
        mockMvc.perform(get("/products")
                        .param("page", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // Assert first two items; assumes default sort yields these in order (id ascending is typical)
                .andExpect(jsonPath("$.data[0].id").value(idA.intValue()))
                .andExpect(jsonPath("$.data[0].name").value("A"))
                .andExpect(jsonPath("$.data[0].price").value(10.99))
                .andExpect(jsonPath("$.data[1].id").value(idB.intValue()))
                .andExpect(jsonPath("$.data[1].name").value("B"))
                .andExpect(jsonPath("$.data[1].price").value(12.49));

        // Verify the external list call happened with page=0 and some size
        wiremock.verify(getRequestedFor(urlPathEqualTo("/prices"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", matching("\\d+")));
    }
}