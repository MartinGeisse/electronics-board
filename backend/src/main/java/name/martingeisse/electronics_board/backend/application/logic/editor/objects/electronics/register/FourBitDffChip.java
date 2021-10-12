package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.register;

import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.LeftRightChip12;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.components.Dff;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

public final class FourBitDffChip extends LeftRightChip12 {

    @Override
    public void createComponents(Context context) {
        Net clockNet = context.getNet(getId(), "L0");
        Net clockEnableNet = context.getNet(getId(), "L1");
        for (int i = 0; i < 4; i++) {
            Net dataInputNet = context.getNet(getId(), "L" + (i + 2));
            Net dataOutputNet = context.getNet(getId(), "R" + (i + 2));
            new Dff(context.getSimulationModel(), getId(), clockNet, clockEnableNet, dataInputNet, dataOutputNet);
        }
    }

}
