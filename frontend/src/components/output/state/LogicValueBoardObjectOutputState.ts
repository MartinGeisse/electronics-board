import {LogicValue} from "../event/SimulatorOutputEvent";
import {BoardObjectOutputState} from "./BoardObjectOutputState";

export class LogicValueBoardObjectOutputState extends BoardObjectOutputState {

    value: LogicValue;

    constructor(value: LogicValue) {
        super();
        this.value = value;
    }

}
