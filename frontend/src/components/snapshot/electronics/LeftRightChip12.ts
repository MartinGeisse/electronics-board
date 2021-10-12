import {RectangularBoardObject} from "../RectangularBoardObject";
import {RenderContext} from "../BoardSnapshot";
import {Wirable, WiringAttemptResult} from "./Wirable";
import {Point} from "../../util/Geometry";

// number of pins on each side
const SINGLE_SIDE_PIN_COUNT = 6;

// space between the pin rows, in pin sizes
export const MIDDLE_SPACE = 3;

export class LeftRightChip12 extends RectangularBoardObject implements Wirable {

    implementsWirable: true = true;

    getPinSize() {
        return this.width / (MIDDLE_SPACE + 2);
    }

    getHeight(): number {
        return this.getSingleSidePinCount() * this.getPinSize();
    }

    getSingleSidePinCount() {
        return SINGLE_SIDE_PIN_COUNT;
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        const pinSize = this.getPinSize();
        const singleSidePinCount = this.getSingleSidePinCount();
        const height = singleSidePinCount * pinSize;
        canvasContext.fillStyle = "#808040";
        canvasContext.fillRect(this.x, this.y, this.width, height);
        canvasContext.strokeRect(this.x, this.y, this.width, height);
        canvasContext.beginPath();
        canvasContext.moveTo(this.x + pinSize, this.y);
        canvasContext.lineTo(this.x + pinSize, this.y + height);
        canvasContext.moveTo(this.x + pinSize * (MIDDLE_SPACE + 1), this.y);
        canvasContext.lineTo(this.x + pinSize * (MIDDLE_SPACE + 1), this.y + height);
        for (let i = 1; i < singleSidePinCount; i++) {
            canvasContext.moveTo(this.x, this.y + i * pinSize);
            canvasContext.lineTo(this.x + pinSize, this.y + i * pinSize);
            canvasContext.moveTo(this.x + pinSize * (1 + MIDDLE_SPACE), this.y + i * pinSize);
            canvasContext.lineTo(this.x + pinSize * (2 + MIDDLE_SPACE), this.y + i * pinSize);
        }
        canvasContext.stroke();
        canvasContext.fillStyle = "#404040";
        canvasContext.fillRect(this.x + pinSize, this.y, MIDDLE_SPACE * pinSize, height);
    }

    attemptWiring(x: number, y: number, boardToolRadius: number): WiringAttemptResult {
        x -= this.x;
        y -= this.y;
        if (x < 0 || y < 0 || x >= this.width) {
            return {type: "Miss"};
        }
        const pinSize = this.getPinSize();
        const height = this.getSingleSidePinCount() * pinSize;
        if (y >= height) {
            return {type: "Miss"};
        }
        let rightSide = false;
        if (x >= pinSize * (1 + MIDDLE_SPACE)) {
            rightSide = true;
        } else if (x >= pinSize) {
            return {type: "Reject"};
        }
        const verticalIndex = Math.floor(y / pinSize);
        return {
            type: "Accept",
            objectId: this.id,
            portId: (rightSide ? "R" : "L") + verticalIndex
        };
    }

    getPortCenter(portId: string): Point {
        const pinSize = this.width / (MIDDLE_SPACE + 2);
        const x = this.x + pinSize * (portId.charAt(0) === "L" ? 0.5 : (MIDDLE_SPACE + 1.5));
        const y = this.y + pinSize * (parseInt(portId.substring(1)) + +0.5);
        return {x, y};
    }

}
