package name.martingeisse.electronics_board.backend.application.logic.simulator.output;

public class BoardObjectOutputEvent extends SimulatorOutputEvent {

    private final String boardObjectId;

    public BoardObjectOutputEvent(String boardObjectId) {
        this.boardObjectId = boardObjectId;
    }

    public String getBoardObjectId() {
        return boardObjectId;
    }

}
