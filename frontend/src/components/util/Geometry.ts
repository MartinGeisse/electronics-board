export const MAXIMUM_SCALING_FACTOR = 1 / 10; // hide any "jumping" while moving objects
export const DEFAULT_SCALING_FACTOR = 1 / 10;
export const MINIMUM_SCALING_FACTOR = 1 / 10_000_000;

export type Point = {
    readonly x: number;
    readonly y: number;
}

export const ZeroPoint: Point = {
    x: 0,
    y: 0,
};

export type Delta = {
    readonly dx: number;
    readonly dy: number;
}

export const ZeroDelta: Delta = {
    dx: 0,
    dy: 0,
};

export class BoardTransform {

    // to transform from board (quantum) coordinates to screen coordinates, scaling is applied first, then panning
    scalingFactor: number = DEFAULT_SCALING_FACTOR;
    panningX: number = 0;
    panningY: number = 0;

    applyScreenZoom(factor: number, screenZoomCenter: Point): void {
        if (this.scalingFactor * factor < MINIMUM_SCALING_FACTOR) {
            factor = MINIMUM_SCALING_FACTOR / this.scalingFactor;
        }
        if (this.scalingFactor * factor > MAXIMUM_SCALING_FACTOR) {
            factor = MAXIMUM_SCALING_FACTOR / this.scalingFactor;
        }
        this.scalingFactor *= factor;
        this.panningX = (this.panningX - screenZoomCenter.x) * factor + screenZoomCenter.x;
        this.panningY = (this.panningY - screenZoomCenter.y) * factor + screenZoomCenter.y;
    }

    // noinspection JSUnusedGlobalSymbols
    applyScreenPanning(dx: number, dy: number): void {
        this.panningX += dx;
        this.panningY += dy;
    }

    applyScreenPanningDelta(delta: Delta): void {
        this.panningX += delta.dx;
        this.panningY += delta.dy;
    }

    boardPointToScreen(boardPoint: Point): Point {
        return {
            x: this.scalingFactor * boardPoint.x + this.panningX,
            y: this.scalingFactor * boardPoint.y + this.panningY,
        };
    }

    boardDeltaToScreen(boardDelta: Delta): Delta {
        return {
            dx: boardDelta.dx * this.scalingFactor,
            dy: boardDelta.dy * this.scalingFactor,
        };
    }

    screenPointToBoard(screenPoint: Point): Point {
        return {
            x: (screenPoint.x - this.panningX) / this.scalingFactor,
            y: (screenPoint.y - this.panningY) / this.scalingFactor,
        };
    }

    screenDeltaToBoard(screenDelta: Delta): Delta {
        return {
            dx: screenDelta.dx / this.scalingFactor,
            dy: screenDelta.dy / this.scalingFactor,
        };
    }

    screenDistanceToBoard(distance: number): number {
        return distance / this.scalingFactor;
    }

    configureCanvasContext(canvasContext: CanvasRenderingContext2D) {
        canvasContext.transform(this.scalingFactor, 0, 0, this.scalingFactor, this.panningX, this.panningY);
    }

}
