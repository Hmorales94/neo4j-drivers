package landscape.demo;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.List;
import java.util.Objects;

@RelationshipProperties
public class DriverSupportedTransport {

    @Id
    @GeneratedValue
    private Long id;

    private final List<String> versions;

    @TargetNode
    private final DriverTransport transport;

    public DriverSupportedTransport(List<String> versions, DriverTransport transport) {
        this.versions = versions;
        this.transport = transport;
    }

    public List<String> getVersions() {
        return versions;
    }

    public DriverTransport getTransport() {
        return transport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverSupportedTransport that = (DriverSupportedTransport) o;
        return Objects.equals(versions, that.versions) && Objects.equals(transport, that.transport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versions, transport);
    }
}
