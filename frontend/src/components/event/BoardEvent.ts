import {BoardSnapshot} from "../snapshot/BoardSnapshot";
import {Label} from "../snapshot/BoardObject";
import {createBoardObjectFromData} from "../snapshot/createBoardObjectFromData";
import {MINIMUM_WIDTH, RectangularBoardObject} from "../snapshot/RectangularBoardObject";
import {ClockSource} from "../snapshot/electronics/ClockSource";

export interface BoardEventBase {
    type: string;
}

export interface ClearBoardEvent extends BoardEventBase {
    type: "ClearBoard";
}

export interface BoardSnapshotEvent extends BoardEventBase {
    type: "BoardSnapshot";
    objects: any[];
}

export interface CreateObjectEvent extends BoardEventBase {
    type: "CreateObject";
    object: any;
}

export interface DeleteObjectEvent extends BoardEventBase {
    type: "DeleteObject";
    id: string;
}

export interface MoveObjectEvent extends BoardEventBase {
    type: "MoveObject";
    id: string;
    dx: number;
    dy: number;
}

export interface ResizeObjectEvent extends BoardEventBase {
    type: "ResizeObject";
    id: string;
    dx: number;
    dy: number;
}

export interface ChangeLabelTextEvent extends BoardEventBase {
    type: "ChangeLabelText";
    id: string;
    text: string;
}

export interface ConfigureClockSourceEvent extends BoardEventBase {
    type: "ConfigureClockSource";
    id: string;
    divider: number;
}

export type BoardEvent = ClearBoardEvent | BoardSnapshotEvent |
    CreateObjectEvent | DeleteObjectEvent |
    MoveObjectEvent | ResizeObjectEvent | ChangeLabelTextEvent |
    ConfigureClockSourceEvent;

export function applyEvents(snapshot: BoardSnapshot, events: BoardEvent[]): void {
    events.forEach(event => applyEvent(snapshot, event));
}

export function applyEvent(snapshot: BoardSnapshot, event: BoardEvent): void {
    switch (event.type) {

        case "ClearBoard":
            snapshot.objects = {};
            break;

        case "BoardSnapshot":
            snapshot.objects = {};
            event.objects.forEach((object: any) => snapshot.addObject(createBoardObjectFromData(object)));
            break;

        case "CreateObject":
            snapshot.addObject(createBoardObjectFromData(event.object));
            break;

        case "DeleteObject":
            snapshot.deleteObject(event.id);
            break;

        case "MoveObject":
            if (event.id in snapshot.objects) {
                snapshot.objects[event.id].moveBy(event.dx, event.dy);
            }
            break;

        case "ResizeObject":
            if (event.id in snapshot.objects) {
                const object = snapshot.objects[event.id];
                if (object instanceof RectangularBoardObject) {
                    object.width += event.dx;
                    if (object.width < MINIMUM_WIDTH) {
                        object.width = MINIMUM_WIDTH;
                    }
                }
            }
            break;

        case "ChangeLabelText":
            if (event.id in snapshot.objects) {
                const object = snapshot.objects[event.id];
                if (object instanceof Label) {
                    object.text = event.text;
                }
            }
            break;

        case "ConfigureClockSource":
            if (event.id in snapshot.objects) {
                const object = snapshot.objects[event.id];
                if (object instanceof ClockSource) {
                    object.divider = event.divider;
                }
            }
            break;

        default:
            throw new Error("invalid event type: " + (event as any).type);

    }
}
