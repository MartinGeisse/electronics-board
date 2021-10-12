package name.martingeisse.electronics_board.backend.platform;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {
        packages("name.martingeisse.electronics_board.backend");
        property(ServerProperties.OUTBOUND_CONTENT_LENGTH_BUFFER, 0);
    }

}
