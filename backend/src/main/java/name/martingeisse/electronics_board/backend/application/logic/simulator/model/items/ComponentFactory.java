package name.martingeisse.electronics_board.backend.application.logic.simulator.model.items;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;

/**
 * This interface can be implemented by {@link BoardObject}s to turn them into {@link Component}s in the
 * simulation model.
 */
public interface ComponentFactory {

    // does not return the components because each component adds itself to the SimulationModel
    void createComponents(Context context);

    interface Context {
        ItemBasedSimulationModel getSimulationModel();
        Net getNet(String boardObjectId, String portId);
    }

}
