import {BoardOutputState} from "../state/BoardOutputState";
import {LogicValueBoardObjectOutputState} from "../state/LogicValueBoardObjectOutputState";

export type LogicValue = "HIGH" | "LOW" | "UNKNOWN" | "HIGH_IMPEDANCE";

export interface SimulatorOutputEventBase {
    type: string;
}

export interface ResetOutputStateEvent extends SimulatorOutputEventBase {
    type: "ResetOutputState"
}

export interface BoardObjectOutputEvent extends SimulatorOutputEventBase {
    boardObjectId: string;
}

export interface SetOutputLogicValueEvent extends BoardObjectOutputEvent {
    type: "SetOutputLogicValue";
    value: LogicValue;
}

export type SimulatorOutputEvent = ResetOutputStateEvent | SetOutputLogicValueEvent;

export function applyOutputEvent(state: BoardOutputState, event: SimulatorOutputEvent): void {
    switch (event.type) {

        case "ResetOutputState":
            state.objectState = {};
            break;

        case "SetOutputLogicValue":
            state.objectState[event.boardObjectId] = new LogicValueBoardObjectOutputState(event.value);
            break;

        default:
            console.log("UNKNOWN OUTPUT EVENT: " + event);
            break;

    }
}
