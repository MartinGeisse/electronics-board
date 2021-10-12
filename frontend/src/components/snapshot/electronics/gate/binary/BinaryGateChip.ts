import {LeftRightChip12} from "../../LeftRightChip12";
import {RenderContext} from "../../../BoardSnapshot";

const NUMBER_OF_GATES = 4;
const INVERTER_BUBBLE_RADIUS = 0.13;

export abstract class BinaryGateChip extends LeftRightChip12 {

    render(
        canvasContext: CanvasRenderingContext2D,
        renderContext: RenderContext,
        renderInverterBubble: boolean = false
    ): void {
        super.render(canvasContext, renderContext);
        const pinSize = this.getPinSize();
        for (let i = 0; i < NUMBER_OF_GATES; i++) {
            const pairIndex = Math.floor(i / 2);
            const secondGate = pairIndex * 2 !== i;
            canvasContext.save();
            canvasContext.translate(this.x, this.y + 3 * pairIndex * pinSize);
            if (secondGate) {
                canvasContext.translate(5 * pinSize, pinSize);
                canvasContext.scale(-1, 1);
            }
            canvasContext.beginPath();

            canvasContext.moveTo(pinSize, pinSize * 0.5);
            canvasContext.lineTo(pinSize * 1.25, pinSize * 0.5);
            canvasContext.lineTo(pinSize * 1.25, pinSize * 0.8);
            canvasContext.lineTo(pinSize * 1.5, pinSize * 0.8);

            canvasContext.moveTo(pinSize, pinSize * 1.5);
            canvasContext.lineTo(pinSize * 1.25, pinSize * 1.5);
            canvasContext.lineTo(pinSize * 1.25, pinSize * 1.2);
            canvasContext.lineTo(pinSize * 1.5, pinSize * 1.2);

            const outputNetY = secondGate ? pinSize * 1.5 : pinSize * 0.5;
            if (renderInverterBubble) {
                canvasContext.moveTo(pinSize * (2.3 + 2 * INVERTER_BUBBLE_RADIUS), pinSize);
                canvasContext.arc(pinSize * (2.3 + INVERTER_BUBBLE_RADIUS), pinSize,
                    pinSize * INVERTER_BUBBLE_RADIUS, 0, 2 * Math.PI);
                canvasContext.moveTo(pinSize * (2.3 + 2 * INVERTER_BUBBLE_RADIUS), pinSize);
            } else {
                canvasContext.moveTo(pinSize * 2.3, pinSize);
            }
            canvasContext.lineTo(pinSize * 3, pinSize);
            canvasContext.lineTo(pinSize * 3, outputNetY);
            canvasContext.lineTo(pinSize * 4, outputNetY);

            canvasContext.stroke();
            canvasContext.translate(pinSize * 1.5, pinSize * 0.6);

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
