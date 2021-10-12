import {BoardObject, Dot, Label} from "./BoardObject";
import {ClockSource} from "./electronics/ClockSource";
import {Led} from "./electronics/Led";
import {WireEdge} from "./electronics/WireEdge";
import {AuxiliaryWireNode} from "./electronics/AuxiliaryWireNode";
import {AndGateChip} from "./electronics/gate/binary/AndGateChip";
import {OrGateChip} from "./electronics/gate/binary/OrGateChip";
import {XorGateChip} from "./electronics/gate/binary/XorGateChip";
import {NandGateChip} from "./electronics/gate/binary/NandGateChip";
import {NorGateChip} from "./electronics/gate/binary/NorGateChip";
import {XnorGateChip} from "./electronics/gate/binary/XnorGateChip";
import {BufferChip} from "./electronics/gate/unary/BufferChip";
import {InverterChip} from "./electronics/gate/unary/InverterChip";
import {FourBitDffChip} from "./electronics/register/FourBitDffChip";

type BoardObjectConstructor<T> = (data: T) => T;
const constructorTable: Record<string, BoardObjectConstructor<any>> = {
    "Dot": Dot.from,
    "Label": Label.from,

    "ClockSource": ClockSource.from,
    "Led": Led.from,
    "WireEdge": WireEdge.from,
    "AuxiliaryWireNode": AuxiliaryWireNode.from,

    "BufferChip": BufferChip.from,
    "InverterChip": InverterChip.from,

    "AndGateChip": AndGateChip.from,
    "OrGateChip": OrGateChip.from,
    "XorGateChip": XorGateChip.from,
    "NandGateChip": NandGateChip.from,
    "NorGateChip": NorGateChip.from,
    "XnorGateChip": XnorGateChip.from,

    "FourBitDffChip": FourBitDffChip.from,
};

export function createBoardObjectFromData(boardObjectData: BoardObject): BoardObject {
    const constructor = constructorTable[boardObjectData.type];
    if (!constructor) {
        throw new Error("unknown object type: " + boardObjectData.type);
    }
    return constructor(boardObjectData);
}
