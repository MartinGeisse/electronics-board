import {Board} from "../Board";
import {SimpleOkayDialogBase} from "../util/SimpleOkayDialogBase";
import ClockSourceIcon from "./icons/ClockSource.png";
import LedIcon from "./icons/Led.png";
import BufferIcon from "./icons/Buffer.png";
import InverterIcon from "./icons/Inverter.png";
import AndGateIcon from "./icons/AndGate.png";
import OrGateIcon from "./icons/OrGate.png";
import XorGateIcon from "./icons/XorGate.png";
import NandGateIcon from "./icons/NandGate.png";
import NorGateIcon from "./icons/NorGate.png";
import XnorGateIcon from "./icons/XnorGate.png";
import DffIcon from "./icons/Dff.png";
import {PaletteIcon} from "./icons/PaletteIcon";
import {BoardObject} from "../snapshot/BoardObject";
import {ClockSource} from "../snapshot/electronics/ClockSource";
import {Led} from "../snapshot/electronics/Led";
import {BufferChip} from "../snapshot/electronics/gate/unary/BufferChip";
import {InverterChip} from "../snapshot/electronics/gate/unary/InverterChip";
import {AndGateChip} from "../snapshot/electronics/gate/binary/AndGateChip";
import {OrGateChip} from "../snapshot/electronics/gate/binary/OrGateChip";
import {XorGateChip} from "../snapshot/electronics/gate/binary/XorGateChip";
import {NandGateChip} from "../snapshot/electronics/gate/binary/NandGateChip";
import {NorGateChip} from "../snapshot/electronics/gate/binary/NorGateChip";
import {XnorGateChip} from "../snapshot/electronics/gate/binary/XnorGateChip";
import {FourBitDffChip} from "../snapshot/electronics/register/FourBitDffChip";

const maxRowLength = 10;

type BoardObjectFactory = (props: PaletteDialogProps) => BoardObject;
type Item = {
    image: string;
    factory: BoardObjectFactory,
};

const palette: Item[][] = [
    [
        {image: ClockSourceIcon, factory: props => new ClockSource("", props.x, props.y, props.baseWidth, 1)},
        {image: LedIcon, factory: props => new Led("", props.x, props.y, props.baseWidth)},
        {image: BufferIcon, factory: props => new BufferChip("", props.x, props.y, props.baseWidth * 4)},
        {image: InverterIcon, factory: props => new InverterChip("", props.x, props.y, props.baseWidth * 4)},
    ],
    [
        {image: AndGateIcon, factory: props => new AndGateChip("", props.x, props.y, props.baseWidth * 4)},
        {image: OrGateIcon, factory: props => new OrGateChip("", props.x, props.y, props.baseWidth * 4)},
        {image: XorGateIcon, factory: props => new XorGateChip("", props.x, props.y, props.baseWidth * 4)},
        {image: NandGateIcon, factory: props => new NandGateChip("", props.x, props.y, props.baseWidth * 4)},
        {image: NorGateIcon, factory: props => new NorGateChip("", props.x, props.y, props.baseWidth * 4)},
        {image: XnorGateIcon, factory: props => new XnorGateChip("", props.x, props.y, props.baseWidth * 4)},
    ],
    [
        {image: DffIcon, factory: props => new FourBitDffChip("", props.x, props.y, props.baseWidth * 4)},
    ],
];

export type PaletteDialogProps = {
    board: Board,
    x: number,
    y: number,
    baseWidth: number,
};

export function PaletteDialog(props: PaletteDialogProps) {
    return <SimpleOkayDialogBase title={"New Board Object"} okayButtonLabel={"Cancel"}>
        {close => palette.map(row => <div>
            {row.map((item, index) => <>
                {index > 0 && index % maxRowLength === 0 && <br />}
                <a href="#foo" onClick={() => {props.board.createObject(item.factory(props)); close()}}>
                    <PaletteIcon image={item.image} key={item.image} />
                </a>
            </>)}
        </div>)}
    </SimpleOkayDialogBase>;
}
