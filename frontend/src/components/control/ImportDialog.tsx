import {TextField, Typography} from "@material-ui/core";
import * as React from "react";
import {Board} from "../Board";
import {SimpleFormikDialogBase} from "../util/SimpleFormikDialogBase";
import {FormikHelpers} from "formik/dist/types";
import {object, string} from "yup";

export type ImportDialogProps = {
    board: Board,
}

type FormData = {
    importData: string,
};

function isJsonArray(s: string|undefined): boolean {
    if (!s) {
        return false;
    }
    try {
        const json = JSON.parse(s);
        return ("length" in json);
    } catch (e) {
        return false;
    }
}

/**
 *
 */
export function ImportDialog(props: ImportDialogProps) {

    const validationSchema = object({
        importData: string().defined().required().test("json-check", "Please enter valid exported data.", isJsonArray)
    }).defined();

    async function onSubmit(values: FormData, formikHelpers: FormikHelpers<FormData>, close: () => void) {
        await props.board.import(values.importData);
        close();
    }

    return <SimpleFormikDialogBase<FormData>
        title={"Import Board"}
        initialValues={{importData: ""}}
        validationSchema={validationSchema}
        onSubmit={onSubmit}
        okayButtonLabel={"Import"}
    >
        {formikProps => <>
            <Typography>
                Copy the previously exported data into the text field and click "Import".
            </Typography>
            <TextField
                name={"importData"}
                id={"importData"}
                variant={"outlined"}
                multiline={true}
                rows={10}
                rowsMax={10}
                fullWidth={true}
                margin={"normal"}
                value={formikProps.values.importData}
                defaultValue={formikProps.initialValues.importData}
                error={!!formikProps.errors.importData}
                helperText={formikProps.errors.importData || " "}
                onChange={formikProps.handleChange}
                onBlur={formikProps.handleBlur}
                label={"Import Data"}
            />
        </>}
    </SimpleFormikDialogBase>;
}
