import {Board} from "./Board";
import {Delta, Point, ZeroDelta, ZeroPoint} from "./util/Geometry";
import {AuxiliaryWireNode} from "./snapshot/electronics/AuxiliaryWireNode";
import React, {ReactNode} from "react";
import {MousePosition} from "../util/MousePositionContext";
import {BoardMenu} from "./control/BoardMenu";
import {BoardObjectMenu} from "./control/BoardObjectMenu";
import {PaletteDialog} from "./palette/PaletteDialog";

const SCREEN_TOOL_RADIUS = 15;

export class BoardEventHandler {

    board: Board;
    mousePositionSource: MousePosition;
    mouseScreenPosition: Point = ZeroPoint;
    mouseScreenDelta: Delta = ZeroDelta;
    mouseBoardPosition: Point = ZeroPoint;
    mouseBoardDelta: Delta = ZeroDelta;
    setHoverContent: (content: ReactNode) => void;
    panning: boolean = false;
    dragging: boolean = false;
    resizing: boolean = false;

    constructor(
        board: Board,
        mousePosition: MousePosition,
        setHoverContent: (content: ReactNode) => void,
    ) {
        this.board = board;
        this.mousePositionSource = mousePosition;
        this.setHoverContent = setHoverContent;
    }

    private updateMousePosition(): void {
        const newScreenPosition = {
            x: this.mousePositionSource.clientX,
            y: this.mousePositionSource.clientY,
        };
        this.mouseScreenDelta = {
            dx: newScreenPosition.x - this.mouseScreenPosition.x,
            dy: newScreenPosition.y - this.mouseScreenPosition.y
        };
        this.mouseScreenPosition = newScreenPosition;
        this.mouseBoardPosition = this.board.transform.screenPointToBoard(newScreenPosition);
        this.mouseBoardDelta = this.board.transform.screenDeltaToBoard(this.mouseScreenDelta);
        this.board.mousePosition = this.mouseBoardPosition;
    }

    private selectObjectAtMouse(): boolean {
        const {x, y} = this.mouseBoardPosition;
        return this.board.selectObjectAt(x, y, SCREEN_TOOL_RADIUS, true);
    }

    onMouseDown(event: React.MouseEvent): boolean|void {
        this.updateMousePosition();
        switch (event.button) {

            case 0:
                if (event.getModifierState("Shift")) {
                    this.panning = false;
                    this.dragging = this.selectObjectAtMouse();
                    this.resizing = false;
                } else if (event.getModifierState("Control")) {
                    this.panning = false;
                    this.dragging = false;
                    this.resizing = this.selectObjectAtMouse();
                } else {
                    this.panning = true;
                    this.dragging = false;
                    this.resizing = false;
                }
                break;

            case 2:
                // right button: object manipulation
                /*
                if (!this.selectObjectAtMouse()) {
                    // TODO
                    // if (event.getModifierState("Shift")) {
                    //     this.board.createObject(new Dot("", x, y));
                    // } else if (event.getModifierState("Control")) {
                    //     this.board.createObject(new Label("", x, y, "Foobar"));
                    // }
                } else {
                    // TODO
                    // if (event.getModifierState("Alt")) {
                    //     const selectedObject = this.board.getSelectedObject();
                    //     if (selectedObject instanceof Label) {
                    //         const text = prompt("label text", selectedObject.text);
                    //         if (text !== null) {
                    //             this.board.addEvent({
                    //                 type: "ChangeLabelText",
                    //                 id: selectedObject.id,
                    //                 text,
                    //             })
                    //         }
                    //     }
                    // }
                }
                */
                this.selectObjectAtMouse();
                if (this.board.selectedObjectId) {
                    // We need to open the board context menu after this mouse event has finished, otherwise we'll open
                    // it under the mouse cursor and the "contextmenu" event from the right-click goes to the board
                    // context menu, which does not prevent it and causes the browser context menu to open on top of it.
                    setTimeout(() => this.setHoverContent(<BoardObjectMenu screenPosition={this.mouseScreenPosition}
                        board={this.board} setHoverContent={this.setHoverContent} boardEventHandler={this} />), 0);
                } else {
                    setTimeout(() => this.setHoverContent(<BoardMenu screenPosition={this.mouseScreenPosition}
                        board={this.board} setHoverContent={this.setHoverContent} />), 0);
                }
                this.board.render();
                return false;

        }
    }

    onMouseUp(event: React.MouseEvent): void {
        this.updateMousePosition();

        switch (event.button) {

            case 0:
                this.panning = false;
                this.dragging = false;
                this.resizing = false;
                break;

        }
    }

    onMouseMove(event: React.MouseEvent): void {
        this.updateMousePosition();
        const selectedObject = this.board.getSelectedObject();
        if (this.panning) {
            this.board.transform.applyScreenPanningDelta(this.mouseScreenDelta);
            this.board.render();
        }
        if (selectedObject !== null) {
            if (this.dragging) {
                this.board.addEvent({
                    type: "MoveObject",
                    id: selectedObject.id,
                    dx: this.mouseBoardDelta.dx,
                    dy: this.mouseBoardDelta.dy,
                })
            }
            if (this.resizing) {
                this.board.addEvent({
                    type: "ResizeObject",
                    id: selectedObject.id,
                    dx: this.mouseBoardDelta.dx,
                    dy: this.mouseBoardDelta.dy,
                })
            }
        }
        if (this.board.previousWireNodeId) {
            this.board.render();
        }
    }

    onWheel(event: React.WheelEvent): void {
        this.updateMousePosition();
        this.board.transform.applyScreenZoom(Math.pow(2, -event.deltaY / 100), this.mouseScreenPosition);
        this.board.render();
    }

    onKeyDown(event: React.KeyboardEvent): void {
        this.updateMousePosition();
        if (event.repeat) {
            return;
        }
        const {x, y} = this.mouseBoardPosition;
        switch (event.key) {

            case "n":
                this.setHoverContent(<PaletteDialog
                    board={this.board}
                    x={this.mouseBoardPosition.x}
                    y={this.mouseBoardPosition.y}
                    baseWidth={this.board.transform.screenDistanceToBoard(50)}
                />);
                break;

            case " ":
                this.board.performWiring(x, y, SCREEN_TOOL_RADIUS);
                break;

            case "w":
                this.board.createObject(new AuxiliaryWireNode("", x, y));
                break;

            case "Delete":
                this.board.deleteSelectedObject();
                break;

            case "Escape":
                this.board.previousWireNodeId = null;
                break;

            case "1":
                // noinspection JSIgnoredPromiseFromCall
                this.board.startSimulation();
                break;

            case "2":
                // noinspection JSIgnoredPromiseFromCall
                this.board.stopSimulation();
                break;

            case "p": {
                this.selectObjectAtMouse();
                const selectedId = this.board.selectedObjectId;
                if (selectedId) {
                    const object = this.board.snapshot.objects[selectedId];
                    if (object) {
                        this.setHoverContent(object.createDialog(this.board));
                    }
                }
                break;
            }

            default:
                console.log("other key: " + event.key);
                break;

        }
    }

}
