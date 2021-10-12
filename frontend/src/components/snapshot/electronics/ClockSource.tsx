import {RenderContext} from "../BoardSnapshot";
import {RectangularBoardObject} from "../RectangularBoardObject";
import {Wirable, WiringAttemptResult} from "./Wirable";
import {Point} from "../../util/Geometry";
import {ClockSourceDialog} from "./ClockSourceDialog";
import {Board} from "../../Board";

export class ClockSource extends RectangularBoardObject implements Wirable {

    implementsWirable: true = true;
    divider: number;

    constructor(id: string, x: number, y: number, width: number, divider: number) {
        super(id, "ClockSource", x, y, width);
        this.divider = divider;
    }

    static from(other: ClockSource): ClockSource {
        return new ClockSource(other.id, other.x, other.y, other.width, other.divider);
    }

    getHeight(): number {
        // noinspection JSSuspiciousNameCombination
        return this.width; // square
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        canvasContext.strokeRect(this.x, this.y, this.width, this.width);
        canvasContext.beginPath();
        canvasContext.moveTo(this.x, this.y);
        canvasContext.lineTo(this.x + this.width, this.y + this.width);
        canvasContext.moveTo(this.x + this.width, this.y);
        canvasContext.lineTo(this.x, this.y + this.width);
        canvasContext.stroke();
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

    createDialog(board: Board) {
        return <ClockSourceDialog board={board} clockSource={this} />;
    }

}
