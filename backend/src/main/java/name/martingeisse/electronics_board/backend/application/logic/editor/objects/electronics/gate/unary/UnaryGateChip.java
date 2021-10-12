package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.gate.unary;

import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.LeftRightChip12;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

public abstract class UnaryGateChip extends LeftRightChip12 {

    private static final int NUMBER_OF_GATES = 6;

    @Override
    public void createComponents(Context context) {
        for (int i = 0; i < NUMBER_OF_GATES; i++) {
            createUnaryGate(context, "L" + i, "R" + i);
        }
    }

    private void createUnaryGate(Context context, String inputNet, String outputNet) {
        createUnaryGate(context, context.getNet(getId(), inputNet), context.getNet(getId(), outputNet));
    }

    protected abstract void createUnaryGate(Context context, Net inputNet, Net outputNet);

}
