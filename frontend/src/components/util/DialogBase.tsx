import {Dialog} from "@material-ui/core";
import * as React from "react";
import {ReactElement, ReactNode, SyntheticEvent, useState} from "react";

export type DialogBaseProps = {
    children: (close: () => void) => ReactNode,
};

export function DialogBase(props: DialogBaseProps): ReactElement {
    const [open, setOpen] = useState(true);
    function close() {
        setOpen(false);
    }
    return <Dialog open={open}
        onEscapeKeyDown={(e: SyntheticEvent<{}>) => close()}
        onBackdropClick={(e: SyntheticEvent<{}>) => close()}
    >
        {props.children(close)}
    </Dialog>
}
