import React, {createContext, useRef} from "react";

export type MousePosition = {
    clientX: number;
    clientY: number;
};

const DEFAULT_POSITION: MousePosition = {
    clientX: 0,
    clientY: 0,
}

/**
 * Note that this context uses the same position object all the time, so changes in mouse coordinates will not cause
 * re-rendering of any components. This is intentional. Use an onMouseMove event to react to changes. This context
 * is only meant to provide the mouse position to keyboard event handlers which otherwise cannot get the mouse
 * position in case of missed onMouseMove events.
 */
export const MousePositionContext = createContext<MousePosition>(DEFAULT_POSITION);

export function MousePositionContextProvider({children}: {children: any}) {
    const ref = useRef<MousePosition>({...DEFAULT_POSITION});
    const mouseHandler = (event: React.MouseEvent): void => {
        ref.current.clientX = event.clientX;
        ref.current.clientY = event.clientY;
    };
    return <div
        onMouseMoveCapture={mouseHandler}
        onMouseDownCapture={mouseHandler}
        onMouseUpCapture={mouseHandler}
        onWheelCapture={mouseHandler}
    >
        <MousePositionContext.Provider value={ref.current}>
            {children}
        </MousePositionContext.Provider>
    </div>;
}
