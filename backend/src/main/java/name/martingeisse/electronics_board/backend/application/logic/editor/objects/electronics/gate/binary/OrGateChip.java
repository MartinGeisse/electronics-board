package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.gate.binary;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.components.OrGate;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

public final class OrGateChip extends BinaryGateChip {

    @Override
    protected void createBinaryGate(Context context, Net inputNet1, Net inputNet2, Net outputNet) {
        new OrGate(context.getSimulationModel(), getId(), inputNet1, inputNet2, outputNet);
    }

}
