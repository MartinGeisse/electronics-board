package name.martingeisse.electronics_board.backend.application.api.generic.v1;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import name.martingeisse.electronics_board.backend.application.logic.simulator.EditorSimulatorBridge;

@Path("/generic/v1/simulator")
public class SimulatorApi {

    @Inject
    private EditorSimulatorBridge editorSimulatorBridge;

    @POST
    @Path("/start")
    public void start() {
        editorSimulatorBridge.start();
    }

    @POST
    @Path("/stop")
    public void stop() {
        editorSimulatorBridge.stop();
    }

}
