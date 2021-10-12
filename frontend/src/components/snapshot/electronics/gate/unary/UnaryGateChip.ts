import {LeftRightChip12} from "../../LeftRightChip12";
import {RenderContext} from "../../../BoardSnapshot";

const NUMBER_OF_GATES = 6;
const INVERTER_BUBBLE_RADIUS = 0.13;

export abstract class UnaryGateChip extends LeftRightChip12 {

    render(
        canvasContext: CanvasRenderingContext2D,
        renderContext: RenderContext,
        renderInverterBubble: boolean = false
    ): void {
        super.render(canvasContext, renderContext);
        const pinSize = this.getPinSize();
        for (let i = 0; i < NUMBER_OF_GATES; i++) {
            canvasContext.save();
            canvasContext.translate(this.x, this.y + i * pinSize);
            canvasContext.beginPath();

            const netY = pinSize * 0.5;
            canvasContext.moveTo(pinSize, netY);
            canvasContext.lineTo(pinSize * 2.1, netY);
            if (renderInverterBubble) {
                canvasContext.moveTo(pinSize * (2.9 + 2 * INVERTER_BUBBLE_RADIUS), netY);
                canvasContext.arc(pinSize * (2.9 + INVERTER_BUBBLE_RADIUS), netY,
                    pinSize * INVERTER_BUBBLE_RADIUS, 0, 2 * Math.PI);
                canvasContext.moveTo(pinSize * (2.9 + 2 * INVERTER_BUBBLE_RADIUS), netY);
            } else {
                canvasContext.moveTo(pinSize * 2.9, netY);
            }
            canvasContext.lineTo(pinSize * 4, netY);
            canvasContext.stroke();

            canvasContext.translate(pinSize * 2.1, netY - 0.4 * pinSize);
            const scale = pinSize * 0.8;
            canvasContext.lineWidth /= scale;
            canvasContext.scale(scale, scale);
            canvasContext.fillStyle = "#808080";
            this.renderGate(canvasContext, renderContext);
            canvasContext.restore();
        }

    }

    /**
     * Renders a single gate using (roughly) the (0,0)..(1,1) rectangle. The input wires end at (0, 0.25) and
     * (0, 0.75). The output wire starts at (1, 0.5).
     */
    abstract renderGate(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void;

}
