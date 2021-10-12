import {BoardObject} from "../BoardObject";
import {WireNodeId, wireNodeIdsEqual} from "./Wirable";
import {BoardSnapshot, RenderContext} from "../BoardSnapshot";

export class WireEdge extends BoardObject {

    node1: WireNodeId;
    node2: WireNodeId;

    constructor(id: string, node1: WireNodeId, node2: WireNodeId) {
        super(id, "WireEdge");
        this.node1 = node1;
        this.node2 = node2;
    }

    static from(other: WireEdge): WireEdge {
        return new WireEdge(other.id, other.node1, other.node2);
    }

    render(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext): void {
        const point1 = renderContext.board.snapshot.getWireNodeCenter(this.node1);
        const point2 = renderContext.board.snapshot.getWireNodeCenter(this.node2);
        if (point1 && point2) {
            canvasContext.strokeStyle = "#80ff80";
            canvasContext.beginPath();
            canvasContext.moveTo(point1.x, point1.y);
            canvasContext.lineTo(point2.x, point2.y);
            canvasContext.stroke();
        } else {
            console.log("WireEdge is missing an endpoint!");
        }
    }

    doesConnect(node1: WireNodeId, node2: WireNodeId) {
        return (wireNodeIdsEqual(node1, this.node1) && wireNodeIdsEqual(node2, this.node2)) ||
            (wireNodeIdsEqual(node1, this.node2) && wireNodeIdsEqual(node2, this.node1));
    }

    existenceDependsOnObject(other: BoardObject): boolean {
        return this.node1.objectId === other.id || this.node2.objectId === other.id;
    }

}

export function boardSnapshotHasWireEdge(boardSnapshot: BoardSnapshot, node1: WireNodeId, node2: WireNodeId) {
    return boardSnapshot.findObject(o => o instanceof WireEdge && o.doesConnect(node1, node2)) !== null;
}
