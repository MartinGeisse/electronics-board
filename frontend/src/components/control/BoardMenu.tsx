import React, {ReactNode, useState} from "react";
import {Divider, Menu, MenuItem} from "@material-ui/core";
import {ExportDialog} from "./ExportDialog";
import {Board} from "../Board";
import {ImportDialog} from "./ImportDialog";
import {PaletteDialog} from "../palette/PaletteDialog";
import {Point} from "../util/Geometry";

export type BoardMenuProps = {
    screenPosition: Point,
    board: Board,
    setHoverContent: (content: ReactNode) => void,
}

function confirmAndClearBoard(board: Board) {
    if (window.confirm("Really clear the board?")) {
        board.addEvent({type: "ClearBoard"});
    }
}

export function BoardMenu(props: BoardMenuProps) {
    const [open, setOpen] = useState(true);
    const boardPosition = props.board.transform.screenPointToBoard(props.screenPosition);
    return <Menu
        autoFocus={false}
        variant={"menu"}
        open={open}
        onClose={() => setOpen(false)}
        anchorReference="anchorPosition"
        anchorPosition={{left: props.screenPosition.x, top: props.screenPosition.y}}
    >
        <MenuItem onClick={() => props.setHoverContent(<PaletteDialog board={props.board}
            x={boardPosition.x} y={boardPosition.y} baseWidth={props.board.transform.screenDistanceToBoard(50)} />)}>
            New Object...
        </MenuItem>
        <Divider />
        <MenuItem onClick={() => props.setHoverContent(<ExportDialog board={props.board} history={true} />)}>Export with history</MenuItem>
        <MenuItem onClick={() => props.setHoverContent(<ExportDialog board={props.board} history={false} />)}>Export without history</MenuItem>
        <MenuItem onClick={() => props.setHoverContent(<ImportDialog board={props.board} />)}>Import</MenuItem>
        <MenuItem onClick={() => {confirmAndClearBoard(props.board); setOpen(false)}}>Clear</MenuItem>
    </Menu>
}
