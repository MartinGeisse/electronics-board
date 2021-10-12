import React, {MutableRefObject} from "react";
import {BoardSnapshot} from "./snapshot/BoardSnapshot";
import {applyEvent, applyEvents, BoardEvent, BoardSnapshotEvent} from "./event/BoardEvent";
import {v4 as uuidv4} from "uuid";
import {BoardObject} from "./snapshot/BoardObject";
import {BoardTransform, Point} from "./util/Geometry";
import {asWirable, WireNodeId} from "./snapshot/electronics/Wirable";
import {boardSnapshotHasWireEdge, WireEdge} from "./snapshot/electronics/WireEdge";
import {AuxiliaryWireNode} from "./snapshot/electronics/AuxiliaryWireNode";
import {BoardOutputState} from "./output/state/BoardOutputState";
import {BACKEND_REST_BASE_URL} from "../SystemConfiguration";
import {mapMapToUnsortedArray} from "../util/mapMap";

export const host = "192.168.1.77";

export class Board {

    canvasRef: MutableRefObject<null | HTMLCanvasElement>;
    snapshot: BoardSnapshot;
    transform: BoardTransform = new BoardTransform();
    selectedObjectId: string | null = null;
    previousWireNodeId: WireNodeId | null = null;
    mousePosition: Point = {x: 0, y: 0};
    outputState: BoardOutputState = new BoardOutputState();

    constructor(canvasRef: React.MutableRefObject<HTMLCanvasElement | null>) {
        this.canvasRef = canvasRef;
        this.snapshot = new BoardSnapshot();
    }

    // the ID of the template is ignored here, and no reference to the template gets stored
    // returns the ID actually assigned
    createObject(template: BoardObject): string {
        const object = {...template, id: uuidv4()};
        delete (object as any).implementsWirable;
        this.addEvent({type: "CreateObject", object});
        return object.id;
    }

    addEvent(event: BoardEvent) {
        // TODO handle failed API call
        fetch(BACKEND_REST_BASE_URL + "/editor?parentId=0", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(event),
        })
        applyEvent(this.snapshot, event);
        this.render();
    }

    async fetchAndRender() {
        // TODO handle failed API call
        // TODO merge, don't just re-fetch everything
        // TODO transform incoming events
        // TODO (check this?) do not transfer event IDs, we don't need them. Just transfer event bodies.
        const response = await fetch(BACKEND_REST_BASE_URL + "/editor");
        const json: any[] = await response.json();
        const events = json.map((serverEvent: any) => ({
            id: serverEvent.id,
            ...serverEvent.body
        }));

        this.snapshot = new BoardSnapshot();
        applyEvents(this.snapshot, events);
        this.render();
    }

    render() {

        // get canvas
        const canvas = this.canvasRef.current;
        if (!canvas) {
            return;
        }

        // handle resizing
        if (canvas.width !== canvas.clientWidth || canvas.height !== canvas.clientHeight) {
            canvas.width = canvas.clientWidth;
            canvas.height = canvas.clientHeight;
        }

        // get canvas context
        const canvasContext = canvas.getContext("2d");
        if (!canvasContext) {
            return;
        }

        // reset transformation; clear to black before applying the transformation, so it fits the actual canvas extents
        canvasContext.resetTransform();
        canvasContext.fillStyle = "black";
        canvasContext.fillRect(0, 0, canvasContext.canvas.width, canvasContext.canvas.height);
        this.transform.configureCanvasContext(canvasContext);

        // render board contents
        canvasContext.setLineDash([]);
        this.snapshot.renderObjects(canvasContext, {board: this});

        // render wiring UI
        if (this.previousWireNodeId) {
            const previousWireNodePosition = this.snapshot.getWireNodeCenter(this.previousWireNodeId);
            if (previousWireNodePosition) {
                const dashLength = this.transform.screenDistanceToBoard(10);
                canvasContext.setLineDash([dashLength, dashLength]);
                canvasContext.fillStyle = canvasContext.strokeStyle = "red";
                canvasContext.lineWidth = 2 / this.transform.scalingFactor;
                canvasContext.beginPath();
                canvasContext.moveTo(previousWireNodePosition.x, previousWireNodePosition.y);
                canvasContext.lineTo(this.mousePosition.x, this.mousePosition.y);
                canvasContext.stroke();
            }
        }

    }

    selectNone(): void {
        this.selectedObjectId = null;
    }

    selectObjectAt(x: number, y: number, screenToolRadius: number, deselectIfNoneFound: boolean): boolean {
        const object = this.findObjectAt(x, y, screenToolRadius);
        if (object === null) {
            if (deselectIfNoneFound) {
                this.selectedObjectId = null;
            }
            return false;
        } else {
            this.selectedObjectId = object.id;
            return true;
        }
    }

    getSelectedObject(): BoardObject | null {
        return this.selectedObjectId === null ? null : this.snapshot.objects[this.selectedObjectId];
    }

    forEachObject(body: (object: BoardObject) => any) {
        this.snapshot.forEachObject(body);
    }

    // noinspection JSUnusedGlobalSymbols
    findObject(predicate: (o: BoardObject) => boolean): BoardObject | null {
        return this.snapshot.findObject(predicate);
    }

    findObjectAt(x: number, y: number, screenToolRadius: number): BoardObject | null {
        const boardToolRadius = this.transform.screenDistanceToBoard(screenToolRadius);
        return this.snapshot.findObjectAt(x, y, boardToolRadius);
    }

    // noinspection JSUnusedGlobalSymbols
    findObjects(predicate: (o: BoardObject) => boolean): BoardObject[] {
        return this.snapshot.findObjects(predicate);
    }

    findObjectsAt(x: number, y: number, screenToolRadius: number): BoardObject[] {
        const boardToolRadius = this.transform.screenDistanceToBoard(screenToolRadius);
        return this.snapshot.findObjectsAt(x, y, boardToolRadius);
    }

    performWiring(x: number, y: number, screenToolRadius: number): void {
        const boardToolRadius = this.transform.screenDistanceToBoard(screenToolRadius);
        let wired = false, rejected = false;
        this.snapshot.forEachObject(o => {
            const wirable = asWirable(o);
            if (wirable) {
                const result = wirable.attemptWiring(x, y, boardToolRadius);
                if (result.type === "Accept") {
                    // If the user is trying to directly connect two ports of the same object, just select the new
                    // port. We want to enforce at least one extra node so it doesn't look too ugly.
                    if (this.previousWireNodeId && this.previousWireNodeId.objectId !== result.objectId) {
                        const newWireNodeId = {objectId: result.objectId, portId: result.portId};
                        if (!boardSnapshotHasWireEdge(this.snapshot, this.previousWireNodeId, newWireNodeId)) {
                            this.createObject(new WireEdge("", this.previousWireNodeId, newWireNodeId));
                        }
                        this.previousWireNodeId = null;
                    } else {
                        this.previousWireNodeId = {objectId: result.objectId, portId: result.portId};
                    }
                    this.render();
                    wired = true;
                    return true;
                } else if (result.type === "Reject") {
                    rejected = true;
                }
            }
        });
        if (!wired && !rejected && this.previousWireNodeId) {
            const newNodeObjectId = this.createObject(new AuxiliaryWireNode("", x, y));
            const newNodeId: WireNodeId = {objectId: newNodeObjectId, portId: AuxiliaryWireNode.SINGLETON_PORT_ID};
            this.createObject(new WireEdge("", this.previousWireNodeId, newNodeId));
            this.previousWireNodeId = newNodeId;
            this.render();
        }
    }

    findAndDeleteObject(x: number, y: number, screenToolRadius: number): void {
        const object = this.findObjectAt(x, y, screenToolRadius);
        if (object !== null) {
            this.addEvent({type: "DeleteObject", id: object.id});
        }
    }

    deleteObject(id: string): void {
        this.addEvent({type: "DeleteObject", id});
    }

    deleteSelectedObject(): void {
        if (this.selectedObjectId !== null) {
            this.deleteObject(this.selectedObjectId);
        }
    }

    async startSimulation(): Promise<void> {
        await fetch(BACKEND_REST_BASE_URL + "/simulator/start", {method: "POST"});
    }

    async stopSimulation(): Promise<void> {
        await fetch(BACKEND_REST_BASE_URL + "/simulator/stop", {method: "POST"});
    }

    async export(history: boolean): Promise<string> {
        // Even when we export without history, we'll re-fetch the whole event log and then collapse it to add some
        // fault tolerance over using the accumulated snapshot. They *should* be the same, but in case of bugs while
        // merging the events into the snapshot, we want to err on the safe side. This has the same effect has
        // reloading the board before exporting.
        const response = await fetch(BACKEND_REST_BASE_URL + "/editor");
        const json: any[] = await response.json();
        if (history) {
            return JSON.stringify(json.map(event => event.body));
        } else {
            const tempSnapshot = new BoardSnapshot();
            applyEvents(tempSnapshot, json.map(event => ({id: event.id, ...event.body})));
            const snapshotEvent: BoardSnapshotEvent = {
                type: "BoardSnapshot",
                objects: mapMapToUnsortedArray(tempSnapshot.objects, object => {
                    const objectData = {...object};
                    delete (objectData as any).implementsWirable;
                    return objectData;
                }),
            };
            return JSON.stringify([snapshotEvent]);
        }
    }

    async import(importData: string): Promise<void> {
        // TODO should validate everything server-side, and only if okay, import everything at once
        this.addEvent({type: "ClearBoard"});
        for (const event of JSON.parse(importData)) {
            this.addEvent(event);
        }
    }

}
