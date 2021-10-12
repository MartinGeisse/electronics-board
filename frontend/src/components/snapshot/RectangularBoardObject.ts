import {BoardObject} from "./BoardObject";
import {MAXIMUM_SCALING_FACTOR} from "../util/Geometry";

// minimum width is 30px when fully zoomed in
export const MINIMUM_WIDTH = 30 / MAXIMUM_SCALING_FACTOR;

/**
 * Whether the height is computed from the width, stored explicitly, or whatever, depends on the subclass.
 */
export abstract class RectangularBoardObject extends BoardObject {

    x: number;
    y: number;
    width: number;

    protected constructor(id: string, type: string, x: number, y: number, width: number) {
        super(id, type);
        this.x = x;
        this.y = y;
        this.width = width;
    }

    abstract getHeight(): number;

    moveBy(dx: number, dy: number): void {
        this.x += dx;
        this.y += dy;
    }

    spansPoint(x: number, y: number): boolean {
        return (x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.getHeight());
    }

}
