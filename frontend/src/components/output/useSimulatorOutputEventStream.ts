import {useEffect} from "react";
import {SimulatorOutputEvent} from "./event/SimulatorOutputEvent";
import {BACKEND_WEBSOCKET_BASE_URL} from "../../SystemConfiguration";

export function useSimulatorOutputEventStream(callback: (event: SimulatorOutputEvent) => void) {
    useEffect(() => {
        const webSocket = new WebSocket(BACKEND_WEBSOCKET_BASE_URL + "/simulator/events");
        webSocket.onmessage = (event) => {
            callback(JSON.parse(event.data) as SimulatorOutputEvent);
        };
        return () => webSocket.close();
    }, [callback]);
}
