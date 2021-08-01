package landscape.demo;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;

@Node(primaryLabel = "Driver")
public class Neo4jDriver {

    @Id
    @GeneratedValue(GeneratedValue.UUIDGenerator.class)
    private UUID id;
    private final String uri;
    private final boolean official;
    private final String transport;

    public Neo4jDriver(String uri, boolean official, String transport) {
        this.uri = uri;
        this.official = official;
        this.transport = transport;
    }

    private Neo4jDriver(Neo4jDriver neo4jDriver) {
        this.uri = neo4jDriver.uri;
        this.official = neo4jDriver.official;
        this.transport = neo4jDriver.transport;
    }

    public Neo4jDriver withId(UUID uuid) {
        if(id.equals(uuid)) {
            return this;
        } else {
            Neo4jDriver driver = new Neo4jDriver(this);
            driver.id = uuid;
            return driver;
        }
    }
}
