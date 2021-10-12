import React, {ReactNode, useContext, useEffect, useRef, useState} from "react";
import {Board} from "./Board";
import {BoardEventHandler} from "./BoardEventHandler";
import {useSharedInstance} from "../util/useSharedInstance";
import {MousePositionContext} from "../util/MousePositionContext";
import {useSimulatorOutputEventStream} from "./output/useSimulatorOutputEventStream";
import {applyOutputEvent} from "./output/event/SimulatorOutputEvent";

function useLiveRenderBoard(board: Board) {
    useEffect(() => {
        const intervalId = setInterval(() => board.fetchAndRender(), 500);
        return () => clearInterval(intervalId);
    }, [board]);
}

function useLiveBoardOutput(board: Board) {
    useSimulatorOutputEventStream(event => {
        applyOutputEvent(board.outputState, event);
        board.render();
    });
}

export function BoardPage() {

    const canvasRef = useRef<HTMLCanvasElement>(null);
    const board = useSharedInstance(() => new Board(canvasRef));
    useLiveRenderBoard(board);
    useLiveBoardOutput(board);

    const [hoverContent, setHoverContentInternal] = useState<ReactNode>(null);
    function setHoverContent(content: ReactNode) {
        if (content === null) {
            setHoverContentInternal(null);
        } else {
            setHoverContentInternal(<React.Fragment key={Math.random()}>{content}</React.Fragment>);
        }
    }

    const mousePositionContext = useContext(MousePositionContext);
    const eventHandler = useSharedInstance(() => {
        return new BoardEventHandler(board, mousePositionContext, setHoverContent);
    })

    return <div>
        <canvas
            tabIndex={0}
            style={{padding: "0px", width: "100vw", height: "100vh"}}
            onContextMenu={e => e.preventDefault()}
            onMouseDown={e => eventHandler.onMouseDown(e)}
            onMouseUp={e => eventHandler.onMouseUp(e)}
            onMouseMove={e => eventHandler.onMouseMove(e)}
            onWheel={e => eventHandler.onWheel(e)}
            onKeyDown={e => eventHandler.onKeyDown(e)}
            ref={canvasRef}
        />
        {hoverContent}
    </div>;
}
