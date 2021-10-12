import {Wirable} from "../../Wirable";
import {RenderContext} from "../../../BoardSnapshot";
import {UnaryGateChip} from "./UnaryGateChip";

export class BufferChip extends UnaryGateChip implements Wirable {

    constructor(id: string, x: number, y: number, width: number) {
        super(id, "BufferChip", x, y, width);
    }

    static from(other: BufferChip): BufferChip {
        return new BufferChip(other.id, other.x, other.y, other.width);
    }

    renderGate(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        BufferChip.renderBuffer(canvasContext, renderContext);
    }

    static renderBuffer(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        canvasContext.beginPath();

        canvasContext.moveTo(0, 0);
        canvasContext.lineTo(1, 0.5);
        canvasContext.lineTo(0, 1);
        canvasContext.lineTo(0, 0);

        canvasContext.fill();
        canvasContext.stroke();
    }

}
