import {Wirable} from "../../Wirable";
import {BinaryGateChip} from "./BinaryGateChip";
import {RenderContext} from "../../../BoardSnapshot";

export class AndGateChip extends BinaryGateChip implements Wirable {

    constructor(id: string, x: number, y: number, width: number) {
        super(id, "AndGateChip", x, y, width);
    }

    static from(other: AndGateChip): AndGateChip {
        return new AndGateChip(other.id, other.x, other.y, other.width);
    }

    renderGate(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        AndGateChip.renderAndGate(canvasContext, renderContext);
    }

    static renderAndGate(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        canvasContext.beginPath();

        canvasContext.moveTo(0.5, 1);
        canvasContext.lineTo(0, 1);
        canvasContext.lineTo(0, 0);
        canvasContext.lineTo(0.5, 0);
        canvasContext.arc(0.5, 0.5, 0.5, 1.5 * Math.PI, 0.5 * Math.PI);

        canvasContext.fill();
        canvasContext.stroke();
    }

}
