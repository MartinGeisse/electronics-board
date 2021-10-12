import {Button, DialogActions, DialogContent, DialogTitle, TextField} from "@material-ui/core";
import * as React from "react";
import {Formik, FormikProps} from 'formik';
import {FormikHelpers} from "formik/dist/types";
import {number, object} from "yup";
import {ClockSource} from "./ClockSource";
import {DialogBase} from "../../util/DialogBase";
import {Board} from "../../Board";

type ParameterType = {
    divider: number;
};

export type ClockSourceDialogProps = {
    board: Board,
    clockSource: ClockSource,
}

export function ClockSourceDialog(props: ClockSourceDialogProps) {

    const initialValues: ParameterType = {
        divider: props.clockSource.divider,
    };

    const generalMessage = "Please enter a positive integer number.";
    const validationSchema = object({
        divider: number().defined().required()
            .typeError(generalMessage)
            .integer(generalMessage)
            .positive(generalMessage),
    }).defined()

    function makeOnSubmit(close: () => void) {
        return function onSubmit(values: ParameterType, formikHelpers: FormikHelpers<ParameterType>) {
            props.board.addEvent({
                type: "ConfigureClockSource",
                id: props.clockSource.id,
                divider: values.divider,
            });
            close();
        };
    }

    return <DialogBase>{close => <>
        <Formik<ParameterType> initialValues={initialValues} onSubmit={makeOnSubmit(close)} validationSchema={validationSchema}>
            {(formikProps: FormikProps<ParameterType>) => <form onSubmit={formikProps.handleSubmit}>
                <DialogTitle style={{borderBottom: "1px solid #ddd"}}>
                    Edit Object
                </DialogTitle>
                <DialogContent>
                    <TextField
                        name={"divider"}
                        id={"divider"}
                        variant={"standard"}
                        margin={"normal"}
                        value={formikProps.values.divider}
                        defaultValue={formikProps.initialValues.divider}
                        error={!!formikProps.errors.divider}
                        helperText={formikProps.errors.divider}
                        onChange={formikProps.handleChange}
                        onBlur={formikProps.handleBlur}
                        label={"Clock Divider"}
                    />
                </DialogContent>
                <DialogActions>
                    <Button variant={"text"} onClick={() => close()}>Cancel</Button>
                    <Button variant={"contained"} type={"submit"}>Ok</Button>
                </DialogActions>
            </form>}
        </Formik>
    </>}</DialogBase>
}
