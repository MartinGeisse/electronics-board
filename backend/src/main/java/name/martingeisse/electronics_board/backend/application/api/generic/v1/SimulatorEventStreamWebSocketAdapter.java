package name.martingeisse.electronics_board.backend.application.api.generic.v1;

import jakarta.inject.Inject;
import name.martingeisse.electronics_board.backend.application.logic.simulator.Simulator;
import name.martingeisse.electronics_board.backend.application.logic.simulator.output.SimulatorOutputEvent;
import name.martingeisse.electronics_board.backend.application.util.ObjectMapperHolder;
import name.martingeisse.electronics_board.backend.application.util.WebSocketEndpoints;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.IOException;
import java.util.function.Consumer;

public class SimulatorEventStreamWebSocketAdapter extends WebSocketAdapter {

    @Inject
    private Simulator simulator;

    private final Consumer<SimulatorOutputEvent> outputEventConsumer = this::onOutputEvent;

    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        simulator.addOutputEventConsumer(outputEventConsumer);
    }

    @Override
    public void onWebSocketText(String message) {
        // ignore for now
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        simulator.removeOutputEventConsumer(outputEventConsumer);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        simulator.removeOutputEventConsumer(outputEventConsumer);
    }

    private void onOutputEvent(SimulatorOutputEvent event) {
        try {
            getSession().getRemote().sendString(ObjectMapperHolder.OBJECT_MAPPER.writeValueAsString(event));
        } catch (IOException e) {
            // ignore -- the simulator is not concerned with a single web socket connection
        }
    }

    public static void defineEndpoints(WebSocketEndpoints.EndpointConsumer consumer) {
        consumer.consumeWebSocketEndpoint("/generic/v1/simulator/events", SimulatorEventStreamWebSocketAdapter::new);
    }

}
