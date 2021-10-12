package name.martingeisse.electronics_board.backend.application.util;

import name.martingeisse.electronics_board.backend.application.api.generic.v1.SimulatorEventStreamWebSocketAdapter;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class WebSocketEndpoints {

    public static void defineEndpoints(EndpointConsumer consumer) {
        SimulatorEventStreamWebSocketAdapter.defineEndpoints(consumer);
    }

    public interface EndpointConsumer {
        void consumeWebSocketEndpoint(String pathSpec, WebSocketAdapterFactory adapterFactory);
    }

    public interface WebSocketAdapterFactory {
        WebSocketAdapter createWebSocketAdapter();
    }

}
