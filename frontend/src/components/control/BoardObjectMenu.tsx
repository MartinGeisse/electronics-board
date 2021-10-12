import {Menu, MenuItem} from "@material-ui/core";
import React, {ReactNode, useState} from "react";
import {BoardEventHandler} from "../BoardEventHandler";
import {Point} from "../util/Geometry";
import {Board} from "../Board";

export type BoardObjectMenuProps = {
    screenPosition: Point,
    board: Board,
    setHoverContent: (content: ReactNode) => void,
    boardEventHandler: BoardEventHandler,
};

export function BoardObjectMenu(props: BoardObjectMenuProps) {
    const [open, setOpen] = useState(true);
    return <Menu
        open={open}
        onClose={() => setOpen(false)}
        anchorReference="anchorPosition"
        anchorPosition={{left: props.screenPosition.x, top: props.screenPosition.y}}
    >
        <MenuItem onClick={() => {
            setOpen(false);
            props.boardEventHandler.panning = false;
            props.boardEventHandler.dragging = true;
        }}>Move</MenuItem>
        <MenuItem onClick={() => {
            const selectedId = props.board.selectedObjectId;
            if (selectedId) {
                const object = props.board.snapshot.objects[selectedId];
                if (object) {
                    props.setHoverContent(object.createDialog(props.board));
                }
            }
        }}>Parameters...</MenuItem>
    </Menu>;
}
