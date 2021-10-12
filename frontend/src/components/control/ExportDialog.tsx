import {TextField, Typography} from "@material-ui/core";
import * as React from "react";
import {SimpleOkayDialogBase} from "../util/SimpleOkayDialogBase";
import {Board} from "../Board";
import {useEffect, useState} from "react";

export type ExportDialogProps = {
    board: Board,
    history: boolean,
}

/**
 * This dialog makes a copy of the exported board data to avoid changes to the data due to incoming events from other
 * users, since such changes would make it harder to copy the data out of the textarea.
 */
export function ExportDialog(props: ExportDialogProps) {
    const [exportData, setExportData] = useState("loading...");
    useEffect(() => {
        (async () => setExportData(await props.board.export(props.history)))();
    });
    return <SimpleOkayDialogBase title={"Export Board"}>
        <Typography>
            Export the board contents {props.history && "and history"} by copying the text below into a text file.
            {!props.history && "The board history will be removed from the export."}
        </Typography>
        <TextField
            name={"exportData"}
            id={"exportData"}
            InputProps={{
                readOnly: true,
            }}
            multiline={true}
            rows={10}
            rowsMax={10}
            variant={"outlined"}
            margin={"normal"}
            fullWidth={true}
            value={exportData}
            label={"Export Data"}
        />
    </SimpleOkayDialogBase>;
}
