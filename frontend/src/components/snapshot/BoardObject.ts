import {RenderContext} from "./BoardSnapshot";
import {BoardObjectOutputState} from "../output/state/BoardObjectOutputState";
import {ReactNode} from "react";
import {Board} from "../Board";

export abstract class BoardObject {

    readonly id: string;
    readonly type: string;

    protected constructor(id: string, type: string) {
        this.id = id;
        this.type = type;
    }

    moveBy(dx: number, dy: number): void {
    }

    spansPoint(x: number, y: number, boardToolRadius: number): boolean {
        return false;
    }

    abstract render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void;

    /**
     * This method is used when the other object gets deleted to determine whether this object has to be deleted too.
     */
    existenceDependsOnObject(other: BoardObject): boolean {
        return false;
    }

    getInitialOutputState(): BoardObjectOutputState | null {
        return null;
    }

    createDialog(board: Board): ReactNode {
        return null;
    }

}

export class Dot extends BoardObject {

    x: number;
    y: number;

    constructor(id: string, x: number, y: number) {
        super(id, "Dot");
        this.x = x;
        this.y = y;
    }

    static from(other: Dot): Dot {
        return new Dot(other.id, other.x, other.y);
    }

    moveBy(dx: number, dy: number): void {
        this.x += dx;
        this.y += dy;
    }

    spansPoint(x: number, y: number): boolean {
        return (x >= this.x - 50 && x < this.x + 50 && y >= this.y - 50 && y < this.y + 50);
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        canvasContext.beginPath();
        canvasContext.arc(this.x, this.y, 50, 0, 2 * Math.PI);
        canvasContext.fill();
    }

}

export class Label extends BoardObject {

    x: number;
    y: number;
    text: string;

    constructor(id: string, x: number, y: number, text: string) {
        super(id, "Label");
        this.x = x;
        this.y = y;
        this.text = text;
    }

    static from(other: Label): Label {
        return new Label(other.id, other.x, other.y, other.text);
    }

    moveBy(dx: number, dy: number): void {
        this.x += dx;
        this.y += dy;
    }

    spansPoint(x: number, y: number): boolean {
        // TODO
        return (x >= this.x && x < this.x + 500 && y >= this.y && y < this.y + 200);
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        const padding = 5;
        const fontSize = 60;
        canvasContext.font = fontSize + "px sans-serif";
        const textMetrics = canvasContext.measureText(this.text);
        const yshift = (textMetrics.actualBoundingBoxAscent + fontSize - textMetrics.actualBoundingBoxDescent) / 2;
        canvasContext.strokeRect(this.x, this.y, textMetrics.width + 2 * padding, fontSize + 2 * padding);
        canvasContext.fillText(this.text, this.x + padding, this.y + padding + yshift);
    }

}
