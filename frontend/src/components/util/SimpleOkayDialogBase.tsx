import {Button, DialogActions, DialogContent, DialogTitle} from "@material-ui/core";
import * as React from "react";
import {ReactNode} from "react";
import {DialogBase} from "./DialogBase";

export type SimpleOkayDialogBaseProps = {
    title: ReactNode,
    children: ReactNode | ((close: () => void) => ReactNode),
    okayButtonLabel?: string | null,
};

/**
 *
 */
export function SimpleOkayDialogBase(props: SimpleOkayDialogBaseProps) {
    return <DialogBase>{close => <>
        <DialogTitle style={{borderBottom: "1px solid #ddd"}}>
            {props.title}
        </DialogTitle>
        <DialogContent>
            {typeof props.children === "function" ? props.children(close) : props.children}
        </DialogContent>
        <DialogActions>
            <Button variant={"contained"} onClick={() => close()}>{props.okayButtonLabel ?? "Ok"}</Button>
        </DialogActions>
    </>}</DialogBase>;
}
