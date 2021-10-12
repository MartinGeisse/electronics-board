import {RenderContext} from "../BoardSnapshot";
import {RectangularBoardObject} from "../RectangularBoardObject";
import {Wirable, WiringAttemptResult} from "./Wirable";
import {Point} from "../../util/Geometry";
import {LogicValueBoardObjectOutputState} from "../../output/state/LogicValueBoardObjectOutputState";

export class Led extends RectangularBoardObject implements Wirable {

    implementsWirable: true = true;

    constructor(id: string, x: number, y: number, width: number) {
        super(id, "Led", x, y, width);
    }

    static from(other: Led): Led {
        return new Led(other.id, other.x, other.y, other.width);
    }

    getHeight(): number {
        // noinspection JSSuspiciousNameCombination
        return this.width; // square
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        canvasContext.strokeRect(this.x, this.y, this.width, this.width);
        canvasContext.beginPath();
        canvasContext.arc(this.x + this.width / 2, this.y + this.width / 2, this.width / 2, 0, 360);
        canvasContext.stroke();
        const state = renderContext.board.outputState.objectState[this.id];
        if (state instanceof LogicValueBoardObjectOutputState) {
            switch (state.value) {

                case "HIGH":
                    canvasContext.fillStyle = "#ff0000";
                    canvasContext.fill();
                    break;

                case "LOW":
                    canvasContext.fillStyle = "#400000";
                    canvasContext.fill();
                    break;

                case "UNKNOWN":
                case "HIGH_IMPEDANCE":
                    canvasContext.fillStyle = "#800000";
                    canvasContext.fill();
                    break;

            }
        }
    }

    attemptWiring(x: number, y: number): WiringAttemptResult {
        if (this.spansPoint(x, y)) {
            return {type: "Accept", objectId: this.id, portId: "0"}
        } else {
            return {type: "Miss"};
        }
    }

    getPortCenter(portId: string): Point {
        return {x: this.x + this.width / 2, y: this.y + this.width / 2};
    }

}