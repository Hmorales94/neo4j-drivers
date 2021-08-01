package landscape.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataNeo4jTest
public class DriverRepositoryTest {

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:4.2");

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
    }

    @AfterEach
    void cleanUp() {
        try (Session session = driver.session()) {
            session.writeTransaction( tx ->
                tx.run("MATCH (n) DETACH DELETE n")
            );
        }
    }

    @Autowired
    DriverRepository repository;

    @Autowired
    Driver driver;

    @Test
    void saves_a_driver() {
        repository.save(new Neo4jDriver("url", false, "HTTP"));

        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
               Result result = tx.run("MATCH (driver:Driver) RETURN driver");
                Record driverRecord = result.single();

                assertThat(driverRecord.get("url").asString())
                        .isEqualTo("url");
                assertThat(driverRecord.get("official").asBoolean())
                       .isFalse();
                assertThat(driverRecord.get("transport").asString())
                        .isEqualTo("HTTP");
                return null;
            });
        }

    }
}
