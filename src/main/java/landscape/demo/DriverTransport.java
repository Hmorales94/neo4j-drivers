package landscape.demo;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Objects;

@Node(labels = "Transport")
public class DriverTransport {

    @Id
    private final DriverTransportType value;

    public DriverTransport(DriverTransportType value) {
        this.value = value;
    }

    public DriverTransportType getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverTransport that = (DriverTransport) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
