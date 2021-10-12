import {RenderContext} from "../BoardSnapshot";
import {Wirable, WiringAttemptResult} from "./Wirable";
import {Point} from "../../util/Geometry";
import {PointShapedBoardObject} from "../PointShapedBoardObject";

const SCREEN_RADIUS = 5;

export class AuxiliaryWireNode extends PointShapedBoardObject implements Wirable {

    static SINGLETON_PORT_ID = "0";

    implementsWirable: true = true;

    constructor(id: string, x: number, y: number) {
        super(id, "AuxiliaryWireNode", x, y);
    }

    static from(other: AuxiliaryWireNode): AuxiliaryWireNode {
        return new AuxiliaryWireNode(other.id, other.x, other.y);
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        const boardRadius = renderContext.board.transform.screenDistanceToBoard(SCREEN_RADIUS);
        canvasContext.beginPath();
        canvasContext.arc(this.x, this.y, boardRadius, 0, 360);
        canvasContext.stroke();
    }

    attemptWiring(x: number, y: number, boardToolRadius: number): WiringAttemptResult {
        if (this.spansPoint(x, y, boardToolRadius)) {
            return {type: "Accept", objectId: this.id, portId: AuxiliaryWireNode.SINGLETON_PORT_ID}
        } else {
            return {type: "Miss"};
        }
    }

    getPortCenter(portId: string): Point {
        return {x: this.x, y: this.y};
    }

}