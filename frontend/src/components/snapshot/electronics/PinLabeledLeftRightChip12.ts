import {LeftRightChip12} from "./LeftRightChip12";
import {RenderContext} from "../BoardSnapshot";

export class PinLabeledLeftRightChip12 extends LeftRightChip12 {

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext) {
        super.render(canvasContext, renderContext);
        canvasContext.font = "200px sans-serif";
        canvasContext.fillStyle = "#ffffff";
        const pinSize = this.getPinSize();
        const pinLabels = this.getPinLabels();
        for (let i = 0; i < pinLabels.length; i++) {
            const label = pinLabels[i];
            const y = this.y + Math.floor(i / 2) * pinSize + pinSize * 0.63;
            if (i % 2 === 0) {
                canvasContext.fillText(label, this.x + pinSize * 1.2, y);
            } else {
                const textMetrics = canvasContext.measureText(label);
                canvasContext.fillText(label, this.x + this.width - pinSize * 1.2 - textMetrics.width, y);
            }
        }
    }

    getPinLabels(): string[] {
        return ["L0", "R0", "L1", "R1", "L2", "R2", "L3", "R3", "L4", "R4", "L5", "R5"];
    }

}