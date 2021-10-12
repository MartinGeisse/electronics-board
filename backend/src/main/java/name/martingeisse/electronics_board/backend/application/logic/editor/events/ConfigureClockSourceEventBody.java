package name.martingeisse.electronics_board.backend.application.logic.editor.events;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEventBody;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.ClockSource;
import name.martingeisse.electronics_board.backend.application.logic.editor.snapshot.MutableBoardSnapshot;

public final class ConfigureClockSourceEventBody extends EditEventBody {

    private String id;
    private int divider;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDivider() {
        return divider;
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }

    @Override
    public void apply(MutableBoardSnapshot snapshot) {
        BoardObject object = snapshot.getObject(id);
        if (object instanceof ClockSource) {
            ClockSource clockSource = (ClockSource)object;
            clockSource.setDivider(divider);
        }
    }

}
