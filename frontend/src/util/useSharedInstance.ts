import {useRef} from "react";

export function useSharedInstance<T>(factory: () => T): T {
    const ref = useRef<T | null>(null);
    if (ref.current === null) {
        ref.current = factory();
    }
    return ref.current;
}
