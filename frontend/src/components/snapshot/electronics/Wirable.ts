import {Point} from "../../util/Geometry";

export type WireNodeId = { objectId: string, portId: string };

export function wireNodeIdsEqual(a: WireNodeId, b: WireNodeId): boolean {
    return a.objectId === b.objectId && a.portId === b.portId;
}

export type WiringAttemptResult =
    { type: "Miss" }
    | { type: "Reject" }
    | { type: "Accept", objectId: string, portId: string };

export interface Wirable {

    implementsWirable: true,

    attemptWiring(x: number, y: number, boardToolRadius: number): WiringAttemptResult;

    getPortCenter(portId: string): Point;

}

export function asWirable(x: unknown): Wirable | null {
    return ((x as any).implementsWirable) ? (x as unknown as Wirable) : null;
}
