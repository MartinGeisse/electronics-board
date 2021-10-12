package name.martingeisse.electronics_board.backend.application.logic.simulator.model.factory;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;
import name.martingeisse.electronics_board.backend.application.logic.editor.snapshot.MutableBoardSnapshot;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.ComponentFactory;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimulationModelFactory {

    public static ItemBasedSimulationModel buildSimulationModel(MutableBoardSnapshot snapshot) {

        NetDetector netDetector = new NetDetector();
        netDetector.addNodesFrom(snapshot);
        netDetector.markNeighborsFrom(snapshot);

        ItemBasedSimulationModel model = new ItemBasedSimulationModel();
        Map<String, Net> nodeIdToNet = new HashMap<>();
        while (true) {
            Set<String> nodeIds = netDetector.detectAndRemoveNet();
            if (nodeIds == null) {
                break;
            }
            Net net = new Net(model);
            for (String nodeId : nodeIds) {
                nodeIdToNet.put(nodeId, net);
            }
        }

        ComponentFactory.Context componentFactoryContext = new ComponentFactory.Context() {

            @Override
            public ItemBasedSimulationModel getSimulationModel() {
                return model;
            }

            @Override
            public Net getNet(String boardObjectId, String portId) {
                return nodeIdToNet.get(NetDetector.getInternalNodeName(boardObjectId, portId));
            }

        };
        for (BoardObject boardObject : snapshot.getObjects()) {
            if (boardObject instanceof ComponentFactory) {
                ComponentFactory componentFactory = (ComponentFactory)boardObject;
                componentFactory.createComponents(componentFactoryContext);
            }
        }

        model.initializeSimulation();
        return model;
    }

}
