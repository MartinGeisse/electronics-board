import {Wirable} from "../../Wirable";
import {BinaryGateChip} from "./BinaryGateChip";
import {RenderContext} from "../../../BoardSnapshot";
import {XorGateChip} from "./XorGateChip";

export class XnorGateChip extends BinaryGateChip implements Wirable {

    constructor(id: string, x: number, y: number, width: number) {
        super(id, "XnorGateChip", x, y, width);
    }

    static from(other: XnorGateChip): XnorGateChip {
        return new XnorGateChip(other.id, other.x, other.y, other.width);
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext) {
        super.render(canvasContext, renderContext, true);
    }

    renderGate(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        XorGateChip.renderXorGate(canvasContext, renderContext);
    }

}
