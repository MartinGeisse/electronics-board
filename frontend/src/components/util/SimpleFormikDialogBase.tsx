import {Button, DialogActions, DialogContent, DialogTitle} from "@material-ui/core";
import * as React from "react";
import {ReactNode} from "react";
import {DialogBase} from "./DialogBase";
import {FormikHelpers} from "formik/dist/types";
import {Formik, FormikProps} from "formik";

export type SimpleFormikDialogBaseProps<T> = {
    title: ReactNode,
    children: ReactNode | ((formikProps: FormikProps<T>, close: () => void) => ReactNode),
    initialValues: T,
    validationSchema: any | (() => any),
    onSubmit: (values: T, formikHelpers: FormikHelpers<T>, close: () => void) => void,
    okayButtonLabel?: ReactNode;
};

/**
 *
 */
export function SimpleFormikDialogBase<T>(props: SimpleFormikDialogBaseProps<T>) {
    return <DialogBase>{close => <>
        <Formik<T>
            initialValues={props.initialValues}
            onSubmit={(values: T, formikHelpers: FormikHelpers<T>) => props.onSubmit(values, formikHelpers, close)}
            validationSchema={props.validationSchema}
        >
            {(formikProps: FormikProps<T>) => <form onSubmit={formikProps.handleSubmit}>
                <DialogTitle style={{borderBottom: "1px solid #ddd"}}>
                    {props.title}
                </DialogTitle>
                <DialogContent>
                    {typeof props.children === "function" ? props.children(formikProps, close) : props.children}
                </DialogContent>
                <DialogActions>
                    <Button variant={"text"} onClick={() => close()}>Cancel</Button>
                    <Button variant={"contained"} type={"submit"}>{props.okayButtonLabel || "Ok"}</Button>
                </DialogActions>
            </form>}
        </Formik>
    </>}</DialogBase>
}
