import {Wirable} from "../Wirable";
import {PinLabeledLeftRightChip12} from "../PinLabeledLeftRightChip12";

export class FourBitDffChip extends PinLabeledLeftRightChip12 implements Wirable {

    constructor(id: string, x: number, y: number, width: number) {
        super(id, "FourBitDffChip", x, y, width);
    }

    static from(other: FourBitDffChip): FourBitDffChip {
        return new FourBitDffChip(other.id, other.x, other.y, other.width);
    }

    getPinLabels(): string[] {
        return ["C", "-", "CE", "-", "D0", "Q0", "D1", "Q1", "D2", "Q2", "D3", "Q3"];
    }

}
