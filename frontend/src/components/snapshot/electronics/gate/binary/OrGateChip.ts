import {Wirable} from "../../Wirable";
import {BinaryGateChip} from "./BinaryGateChip";
import {RenderContext} from "../../../BoardSnapshot";

export class OrGateChip extends BinaryGateChip implements Wirable {

    constructor(id: string, x: number, y: number, width: number) {
        super(id, "OrGateChip", x, y, width);
    }

    static from(other: OrGateChip): OrGateChip {
        return new OrGateChip(other.id, other.x, other.y, other.width);
    }

    renderGate(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        OrGateChip.renderOrGate(canvasContext, renderContext);
    }

    static renderOrGate(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        canvasContext.beginPath();

        const inX = 0.126975;
        canvasContext.moveTo(0, 0.25);
        canvasContext.lineTo(inX, 0.25);
        canvasContext.moveTo(0, 0.75);
        canvasContext.lineTo(inX, 0.75);

        canvasContext.moveTo(0.5, 1);
        canvasContext.lineTo(0, 1);
        canvasContext.arcTo(0.3, 0.5, 0, 0, 0.8);
        canvasContext.lineTo(0, 0);
        canvasContext.lineTo(0.5, 0);
        canvasContext.arc(0.5, 0.5, 0.5, 1.5 * Math.PI, 0.5 * Math.PI);

        canvasContext.fill();
        canvasContext.stroke();
    }

}
