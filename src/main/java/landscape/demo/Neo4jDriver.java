package landscape.demo;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Node(primaryLabel = "Driver")
public class Neo4jDriver {

    @Id
    @GeneratedValue(GeneratedValue.UUIDGenerator.class)
    private UUID id;
    private String uri;
    private boolean official;
    @Relationship(type = "SUPPORTS", direction = Relationship.Direction.OUTGOING)
    private List<DriverSupportedTransport> supportedTransports;

    public Neo4jDriver() {}

    public Neo4jDriver(String uri, boolean official, List<DriverSupportedTransport> supportedTransports) {
        this.uri = uri;
        this.official = official;
        this.supportedTransports = supportedTransports;
    }

    private Neo4jDriver(Neo4jDriver neo4jDriver) {
        this.uri = neo4jDriver.uri;
        this.official = neo4jDriver.official;
        this.supportedTransports = neo4jDriver.supportedTransports;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public List<DriverSupportedTransport> getSupportedTransports() {
        return supportedTransports;
    }

    public void setSupportedTransports(List<DriverSupportedTransport> supportedTransports) {
        this.supportedTransports = supportedTransports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neo4jDriver that = (Neo4jDriver) o;
        return official == that.official && Objects.equals(id, that.id) && Objects.equals(uri, that.uri) && Objects.equals(supportedTransports, that.supportedTransports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uri, official, supportedTransports);
    }
}
