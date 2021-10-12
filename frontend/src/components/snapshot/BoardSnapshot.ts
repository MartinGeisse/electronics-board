import {BoardObject} from "./BoardObject";
import {Board} from "../Board";
import {asWirable, WireNodeId} from "./electronics/Wirable";
import {Point} from "../util/Geometry";

export type RenderContext = {
    board: Board;
}

export class BoardSnapshot {

    objects: { [id: string]: BoardObject } = {};

    addObject(object: BoardObject) {
        this.objects[object.id] = object;
    }

    deleteObject(id: string) {
        const ids = [id];
        while (ids.length > 0) {
            id = ids.pop()!;
            const object = this.objects[id];
            if (!object) {
                continue;
            }
            delete this.objects[id];
            for (const otherObjectId in this.objects) {
                const otherObject = this.objects[otherObjectId];
                if (otherObject.existenceDependsOnObject(object)) {
                    ids.push(otherObject.id);
                }
            }
        }
    }

    forEachObject(body: (object: BoardObject) => any) {
        for (const id in this.objects) {
            if (body(this.objects[id])) {
                return;
            }
        }
    }

    findObject(predicate: (o: BoardObject) => boolean): BoardObject | null {
        let result: BoardObject | null = null;
        this.forEachObject(o => {
            if (predicate(o)) {
                result = o;
                return true;
            }
            return false;
        });
        return result;
    }

    findObjectAt(x: number, y: number, boardToolRadius: number): BoardObject | null {
        return this.findObject(o => o.spansPoint(x, y, boardToolRadius));
    }

    findObjects(predicate: (o: BoardObject) => boolean): BoardObject[] {
        let result: BoardObject[] = [];
        this.forEachObject(o => {
            if (predicate(o)) {
                result.push(o);
            }
            return false;
        });
        return result;
    }

    findObjectsAt(x: number, y: number, boardToolRadius: number): BoardObject[] {
        return this.findObjects(o => o.spansPoint(x, y, boardToolRadius));
    }

    renderObjects(canvasContext: CanvasRenderingContext2D, renderContext: RenderContext) {
        this.forEachObject(o => {
            canvasContext.fillStyle = canvasContext.strokeStyle =
                (o.id === renderContext.board.selectedObjectId) ? "#00ff00" : "white";
            canvasContext.lineWidth = 2 / renderContext.board.transform.scalingFactor;
            o.render(canvasContext, renderContext);
        });
    }

    getWireNodeCenter(nodeId: WireNodeId): Point | null {
        const object = this.objects[nodeId.objectId];
        if (!object) {
            return null;
        }
        const wirable = asWirable(object);
        if (!wirable) {
            return null;
        }
        return wirable.getPortCenter(nodeId.portId);
    }

}
