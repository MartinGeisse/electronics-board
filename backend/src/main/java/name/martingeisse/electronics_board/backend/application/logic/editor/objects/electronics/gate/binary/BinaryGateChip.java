package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.gate.binary;

import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.LeftRightChip12;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

public abstract class BinaryGateChip extends LeftRightChip12 {

    private static final int NUMBER_OF_GATES = 4;

    @Override
    public void createComponents(Context context) {
        for (int i = 0; i < NUMBER_OF_GATES; i++) {
            int x = 3 * (i / 2);
            if (i % 2 == 0) {
                createBinaryGate(context, "L" + x, "L" + (x + 1), "R" + x);
            } else {
                createBinaryGate(context, "R" + (x + 1), "R" + (x + 2), "L" + (x + 2));
            }
        }
    }

    private void createBinaryGate(Context context, String inputNet1, String inputNet2, String outputNet) {
        createBinaryGate(context,
                context.getNet(getId(), inputNet1),
                context.getNet(getId(), inputNet2),
                context.getNet(getId(), outputNet)
        );
    }

    protected abstract void createBinaryGate(Context context, Net inputNet1, Net inputNet2, Net outputNet);

}
