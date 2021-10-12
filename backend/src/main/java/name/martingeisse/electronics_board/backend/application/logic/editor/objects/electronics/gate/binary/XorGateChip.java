package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.gate.binary;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.components.XorGate;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

public final class XorGateChip extends BinaryGateChip {

    @Override
    protected void createBinaryGate(Context context, Net inputNet1, Net inputNet2, Net outputNet) {
        new XorGate(context.getSimulationModel(), getId(), inputNet1, inputNet2, outputNet);
    }

}
