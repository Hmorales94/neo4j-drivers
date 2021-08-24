package landscape.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataNeo4jTest
public class DriverRepositoryTest {

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:4.2")
            .self().withStartupAttempts(10).withAdminPassword("secret");

    @Autowired
    DriverRepository repository;

    @Autowired
    Neo4jTemplate template;

    @Autowired
    Driver driver;

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> "secret");
    }

    @AfterEach
    void cleanUp() {
        try (Session session = driver.session()) {
            session.writeTransaction( tx ->
                tx.run("MATCH (n) DETACH DELETE n")
            );
        }
    }

    @Test
    void saves_a_driver() {
        DriverTransport boltTransport = template.save(new DriverTransport(DriverTransportType.BOLT));
        List<DriverSupportedTransport> supportedTransports = List.of(new DriverSupportedTransport(List.of("4.2"), boltTransport));

        Neo4jDriver neo4jDriver = new Neo4jDriver("https://example.com", false, supportedTransports);
        repository.save(neo4jDriver);

        List<Neo4jDriver> drivers = template.findAll(Neo4jDriver.class);
        assertThat(drivers).hasSize(1)
                .containsExactly(neo4jDriver);

    }
}
