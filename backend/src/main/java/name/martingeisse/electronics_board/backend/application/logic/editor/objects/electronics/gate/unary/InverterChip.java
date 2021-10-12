package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.gate.unary;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.components.NotGate;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

public final class InverterChip extends UnaryGateChip {

    @Override
    protected void createUnaryGate(Context context, Net inputNet, Net outputNet) {
        new NotGate(context.getSimulationModel(), getId(), inputNet, outputNet);
    }

}
