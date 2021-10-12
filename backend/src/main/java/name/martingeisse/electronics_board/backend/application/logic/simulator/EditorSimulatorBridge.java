package name.martingeisse.electronics_board.backend.application.logic.simulator;

import jakarta.inject.Inject;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEvent;
import name.martingeisse.electronics_board.backend.application.logic.editor.snapshot.MutableBoardSnapshot;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.factory.SimulationModelFactory;
import name.martingeisse.electronics_board.backend.application.technical.event_store.EventStore;

public final class EditorSimulatorBridge {

    @Inject
    private EventStore eventStore;

    @Inject
    private Simulator simulator;

    public void start() {
        MutableBoardSnapshot snapshot = new MutableBoardSnapshot();
        for (EditEvent event : eventStore.getAllEvents()) {
            event.getBody().apply(snapshot);
        }
        simulator.setModel(SimulationModelFactory.buildSimulationModel(snapshot));
        simulator.resume();
    }

    public void stop() {
        simulator.pause();
    }

}
