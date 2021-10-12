import {Wirable} from "../../Wirable";
import {BinaryGateChip} from "./BinaryGateChip";
import {RenderContext} from "../../../BoardSnapshot";
import {OrGateChip} from "./OrGateChip";

export class NorGateChip extends BinaryGateChip implements Wirable {

    constructor(id: string, x: number, y: number, width: number) {
        super(id, "NorGateChip", x, y, width);
    }

    static from(other: NorGateChip): NorGateChip {
        return new NorGateChip(other.id, other.x, other.y, other.width);
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext) {
        super.render(canvasContext, renderContext, true);
    }

    renderGate(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        OrGateChip.renderOrGate(canvasContext, renderContext);
    }

}
