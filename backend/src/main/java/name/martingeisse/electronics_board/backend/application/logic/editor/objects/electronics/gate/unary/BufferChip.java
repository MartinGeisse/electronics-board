package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.gate.unary;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.components.Buffer;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

public final class BufferChip extends UnaryGateChip {

    @Override
    protected void createUnaryGate(Context context, Net inputNet, Net outputNet) {
        new Buffer(context.getSimulationModel(), getId(), inputNet, outputNet);
    }

}
