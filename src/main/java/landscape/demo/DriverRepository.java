package landscape.demo;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface DriverRepository extends Neo4jRepository<Neo4jDriver, UUID> {

    Neo4jDriver save(Neo4jDriver driver);
}
