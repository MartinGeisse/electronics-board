import {BoardObject} from "./BoardObject";

/**
 *
 */
export abstract class PointShapedBoardObject extends BoardObject {

    x: number;
    y: number;

    protected constructor(id: string, type: string, x: number, y: number) {
        super(id, type);
        this.x = x;
        this.y = y;
    }

    moveBy(dx: number, dy: number): void {
        this.x += dx;
        this.y += dy;
    }

    spansPoint(x: number, y: number, boardToolRadius: number): boolean {
        const dx = x - this.x;
        const dy = y - this.y;
        const distance = Math.sqrt(dx * dx + dy * dy);
        return (distance <= boardToolRadius);
    }

}
