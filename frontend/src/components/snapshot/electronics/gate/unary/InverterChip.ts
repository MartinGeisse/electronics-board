import {Wirable} from "../../Wirable";
import {RenderContext} from "../../../BoardSnapshot";
import {UnaryGateChip} from "./UnaryGateChip";
import {BufferChip} from "./BufferChip";

export class InverterChip extends UnaryGateChip implements Wirable {

    constructor(id: string, x: number, y: number, width: number) {
        super(id, "InverterChip", x, y, width);
    }

    static from(other: InverterChip): InverterChip {
        return new InverterChip(other.id, other.x, other.y, other.width);
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext) {
        super.render(canvasContext, renderContext, true);
    }

    renderGate(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        BufferChip.renderBuffer(canvasContext, renderContext);
    }

}
