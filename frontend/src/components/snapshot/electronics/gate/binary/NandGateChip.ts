import {Wirable} from "../../Wirable";
import {BinaryGateChip} from "./BinaryGateChip";
import {RenderContext} from "../../../BoardSnapshot";
import {AndGateChip} from "./AndGateChip";

export class NandGateChip extends BinaryGateChip implements Wirable {

    constructor(id: string, x: number, y: number, width: number) {
        super(id, "NandGateChip", x, y, width);
    }

    static from(other: NandGateChip): NandGateChip {
        return new NandGateChip(other.id, other.x, other.y, other.width);
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext) {
        super.render(canvasContext, renderContext, true);
    }

    renderGate(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        AndGateChip.renderAndGate(canvasContext, renderContext);
    }

}
